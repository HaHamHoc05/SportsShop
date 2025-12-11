package com.example.giaybongda.controller;

import com.example.giaybongda.model.CartItem;
import com.example.giaybongda.service.CartService;
import com.example.giaybongda.service.CheckoutService;
import com.example.giaybongda.service.CustomerService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Controller
public class CheckoutController {
    private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerService customerService;
    // bank config injected from application.properties
    @Value("${bank.account.number:}")
    private String bankAccountNumber;
    @Value("${bank.account.name:}")
    private String bankAccountName;
    @Value("${bank.name:}")
    private String bankName;

    @GetMapping("/checkout")
    public String checkoutFrom(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userid");
        if (userId == null) {
            // Chưa đăng nhập -> quay về trang login
            // redirectTo dùng để login xong quay lại checkout
            return "redirect:/login?redirect=/cart";
        }
        List<CartItem> items = cartService.getItems(session);
        model.addAttribute("items", items);
        model.addAttribute("total", cartService.getTotal(session));

        customerService.findById(userId).ifPresent(customer -> {
            model.addAttribute("customer", customer);
        });

        return "bill";
    }

    @PostMapping("/checkout/pay")
    public String doPay(HttpSession session,
                        @RequestParam String tenkh,
                        @RequestParam String email,
                        @RequestParam String sodienthoai,
                        @RequestParam String diachi,
                        @RequestParam String paymentType,
                        Model model) {
        List<CartItem> items = cartService.getItems(session);
        long total = cartService.getTotal(session);
        Integer userid = (Integer) session.getAttribute("userid");

        //san pham kh ton tai
        if (items == null || items.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng rỗng");
            return "bill";
        }

        // Lưu thông tin khách hàng vào session
        session.setAttribute("tenkh", tenkh);
        session.setAttribute("sodienthoai", sodienthoai);
        session.setAttribute("email", email);
        session.setAttribute("diachi", diachi);

        if (userid == null) {
            boolean missing = false;
            StringBuilder sb = new StringBuilder();
            if (tenkh == null || tenkh.trim().isEmpty()) {
                missing = true;
                sb.append("Họ tên không được để trống. ");
            }
            if (email == null || email.trim().isEmpty()) {
                missing = true;
                sb.append("Email không được để trống. ");
            }
            if (sodienthoai == null || sodienthoai.trim().isEmpty()) {
                missing = true;
                sb.append("Số điện thoại không được để trống. ");
            }
            if (diachi == null || diachi.trim().isEmpty()) {
                missing = true;
                sb.append("Địa chỉ không được để trống. ");
            }
            if (missing) {
                model.addAttribute("error", sb.toString());
                model.addAttribute("items", items);
                model.addAttribute("total", total);
                return "bill";
            }
        }

        // xu ly hoa don ( makh neu dang nhap thanh cong)
        Integer makh = userid;
        Integer masohd;
        try {
            masohd = checkoutService.processOrder(session, makh, items, total, tenkh, email, sodienthoai, diachi);
        } catch (Exception ex) {
            // Thông báo lỗi  khi DB từ chối INSERT do makh KHÔNG NULL
            String msg = "Không thể lưu hóa đơn";
            String lower = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
            if (lower.contains("column 'makh' cannot be null") || lower.contains("makh")) {
                msg += "Cơ sở dữ liệu yêu cầu trường makh (customer id) không được null. \n" +
                        "Nếu bạn muốn cho phép khách (guest) thanh toán mà không đăng nhập, hãy chạy migration để cho phép makh NULL và thêm các trường guest. \n" +
                        "(SQL suggestion: ALTER TABLE hoadon MODIFY COLUMN makh INT NULL; ALTER TABLE hoadon ADD COLUMN tenkh VARCHAR(255) NULL, ADD COLUMN email VARCHAR(255) NULL, ADD COLUMN sodienthoai VARCHAR(50) NULL, ADD COLUMN diachi VARCHAR(500) NULL;)";
            } else {
                msg += "Lỗi nội bộ: " + ex.getMessage();
            }
            model.addAttribute("error", msg);
            model.addAttribute("items", items);
            model.addAttribute("total", total);
            // neu tai khoan da dang nhap ,them khach hang de dien truoc bieu mau
            Integer userid2 = (Integer) session.getAttribute("userid");
            if (userid2 != null) {
                customerService.findById(userid2).ifPresent(customer -> model.addAttribute("customer", customer));
            }
            return "bill";
        }

        model.addAttribute("orderId", masohd);
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("paymentType", paymentType);
        //them thong tin kh
        model.addAttribute("tenkh", tenkh);
        model.addAttribute("sodienthoai", sodienthoai);
        model.addAttribute("email", email);
        model.addAttribute("diachi", diachi);

        if ("bank".equals(paymentType)) {

            String payload = "0002010102110216" + bankName + bankAccountNumber
                    + "54" + total // Số tiền
                    + "58VN"
                    + "59" + bankAccountName;

            try {
                // --- Bước 2: Sinh QR bằng ZXing ---
                QRCodeWriter qrWriter = new QRCodeWriter();
                BitMatrix matrix = qrWriter.encode(payload, BarcodeFormat.QR_CODE, 350, 350);

                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
                    String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
                    String dataUrl = "data:image/png;base64," + base64;

                    model.addAttribute("qrDataUrl", dataUrl);
                    model.addAttribute("total", total);

                    log.info("Generated VietQR ZXing QR (bytes={})", base64.length());
                }

            } catch (WriterException we) {
                log.error("Failed to generate QR image: {}", we.getMessage());
            } catch (Exception e) {
                log.error("Unexpected error generating QR image: {}", e.getMessage());
            }

            // expose bank info cho view
            model.addAttribute("bankAccountNumber", bankAccountNumber);
            model.addAttribute("bankAccountName", bankAccountName);
            model.addAttribute("bankName", bankName);
        }


        return "bill_success";

    }

}
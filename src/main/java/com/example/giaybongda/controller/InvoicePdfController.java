package com.example.giaybongda.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class InvoicePdfController {

    // DTO hứng dữ liệu từ Form
    public static class InvoicePdfRequest {
        private String orderId;
        private Long total;
        private String tenkh;
        private String email;
        private String sodienthoai;
        private String diachi;
        private List<Item> items = new ArrayList<>();
        // Getters & Setters
        public String getOrderId() { return orderId; }
        public void setOrderId(String orderId) { this.orderId = orderId; }
        public Long getTotal() { return total; }
        public void setTotal(Long total) { this.total = total; }
        public String getTenkh() { return tenkh; }
        public void setTenkh(String tenkh) { this.tenkh = tenkh; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSodienthoai() { return sodienthoai; }
        public void setSodienthoai(String sodienthoai) { this.sodienthoai = sodienthoai; }
        public String getDiachi() { return diachi; }
        public void setDiachi(String diachi) { this.diachi = diachi; }
        public List<Item> getItems() { return items; }
        public void setItems(List<Item> items) { this.items = items; }

        public static class Item {
            private String name;
            private String sizeLabel;
            private String colorLabel;
            private Long unitPrice;
            private Integer quantity;
            private Long subtotal;
            // Getters & Setters...
            public String getName() { return name; }
            public void setName(String name) { this.name = name; }
            public String getSizeLabel() { return sizeLabel; }
            public void setSizeLabel(String sizeLabel) { this.sizeLabel = sizeLabel; }
            public String getColorLabel() { return colorLabel; }
            public void setColorLabel(String colorLabel) { this.colorLabel = colorLabel; }
            public Long getUnitPrice() { return unitPrice; }
            public void setUnitPrice(Long unitPrice) { this.unitPrice = unitPrice; }
            public Integer getQuantity() { return quantity; }
            public void setQuantity(Integer quantity) { this.quantity = quantity; }
            public Long getSubtotal() { return subtotal; }
            public void setSubtotal(Long subtotal) { this.subtotal = subtotal; }
        }
    }

    @PostMapping("/invoice/pdf")
    public ResponseEntity<byte[]> generatePdf(@ModelAttribute InvoicePdfRequest req) throws IOException {

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            // --- 1. Tải Font Tiếng Việt ---
            // Lưu ý: Phải có file Arial.ttf trong src/main/resources/fonts/
            InputStream fontStream = this.getClass().getResourceAsStream("/fonts/Arial.ttf");
            if (fontStream == null) {
                throw new IOException("Không tìm thấy file font: /fonts/Arial.ttf");
            }
            PDType0Font font = PDType0Font.load(doc, fontStream);

            PDPageContentStream cs = new PDPageContentStream(doc, page);

            // Kích thước trang A4: 595 x 842 points
            float margin = 40;
            float yPosition = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

            // --- 2. Header: Logo & Tên Shop ---
            // Tải logo (Nếu có)
            InputStream logoStream = this.getClass().getResourceAsStream("/images/logo.png");
            if (logoStream != null) {
                // Đọc stream thành mảng byte trước
                byte[] logoBytes = logoStream.readAllBytes();

                // Tạo ảnh từ mảng byte
                PDImageXObject pdImage = PDImageXObject.createFromByteArray(doc, logoBytes, "logo");

                // Vẽ ảnh (x, y, width, height)
                cs.drawImage(pdImage, margin, yPosition - 50, 50, 50);
            }
            // Tên Shop
            cs.setNonStrokingColor(Color.DARK_GRAY);
            drawText(cs, font, 18, margin + 60, yPosition - 20, "SPORTS SHOP");
            cs.setNonStrokingColor(Color.GRAY);
            drawText(cs, font, 10, margin + 60, yPosition - 35, "Chuyên giày bóng đá chính hãng");
            drawText(cs, font, 10, margin + 60, yPosition - 48, "Hotline: 0901 234 567 | Website: giaybongda.vn");

            // Tiêu đề Hóa Đơn (Bên phải)
            cs.setNonStrokingColor(new Color(0, 102, 204)); // Màu xanh dương đậm
            drawTextRight(cs, font, 24, margin + tableWidth, yPosition - 25, "HÓA ĐƠN");
            cs.setNonStrokingColor(Color.BLACK);
            drawTextRight(cs, font, 10, margin + tableWidth, yPosition - 45, "#" + req.getOrderId());
            drawTextRight(cs, font, 10, margin + tableWidth, yPosition - 60, "Ngày: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            yPosition -= 80;

            // --- 3. Thông tin khách hàng (Kẻ khung) ---
            cs.setStrokingColor(Color.LIGHT_GRAY);
            cs.setLineWidth(1f);
            cs.addRect(margin, yPosition - 70, tableWidth, 60); // Vẽ khung chữ nhật
            cs.stroke();

            drawText(cs, font, 11, margin + 10, yPosition - 20, "KHÁCH HÀNG: " + (req.getTenkh() != null ? req.getTenkh().toUpperCase() : ""));
            drawText(cs, font, 10, margin + 10, yPosition - 38, "Địa chỉ: " + req.getDiachi());
            drawText(cs, font, 10, margin + 10, yPosition - 55, "SĐT: " + req.getSodienthoai() + " | Email: " + req.getEmail());

            yPosition -= 90;

            // --- 4. Bảng Sản Phẩm ---
            // Cấu hình cột: Tên SP (40%), Size/Màu (20%), SL (10%), Đơn giá (15%), Thành tiền (15%)
            float[] colWidths = {
                    tableWidth * 0.35f,
                    tableWidth * 0.10f,
                    tableWidth * 0.15f,
                    tableWidth * 0.10f,
                    tableWidth * 0.15f,
                    tableWidth * 0.15f
            };

            String[] headers = {
                    "Sản phẩm",
                    "Size",
                    "Màu",
                    "SL",
                    "Đơn giá",
                    "Thành tiền"
            };

            // Vẽ Header Bảng
            float rowHeight = 20f;
            cs.setNonStrokingColor(new Color(230, 230, 230)); // Màu nền xám nhạt cho header
            cs.addRect(margin, yPosition - rowHeight, tableWidth, rowHeight);
            cs.fill();
            cs.setNonStrokingColor(Color.BLACK); // Quay lại màu chữ đen

            float tableX = margin;
            float currentX = tableX;

            // Vẽ chữ tiêu đề cột
            cs.setFont(font, 10);
            for (int i = 0; i < headers.length; i++) {
                // Căn giữa cho SL, Giá, Thành tiền
                if (i >= 2) {
                    drawTextRight(cs, font, 10, currentX + colWidths[i] - 5, yPosition - 14, headers[i]);
                } else {
                    drawText(cs, font, 10, currentX + 5, yPosition - 14, headers[i]);
                }
                currentX += colWidths[i];
            }
            yPosition -= rowHeight;

            // Vẽ Dữ Liệu Bảng
            NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            if (req.getItems() != null) {
                for (InvoicePdfRequest.Item item : req.getItems()) {
                    // Kiểm tra hết trang
                    if (yPosition < 50) {
                        cs.close();
                        page = new PDPage(PDRectangle.A4);
                        doc.addPage(page);
                        cs = new PDPageContentStream(doc, page);
                        yPosition = page.getMediaBox().getHeight() - margin;
                        // Vẽ lại header bảng nếu qua trang mới (tùy chọn)
                    }

                    currentX = tableX;

                    // Kẻ đường gạch chân mờ
                    cs.setStrokingColor(new Color(220, 220, 220));
                    cs.moveTo(margin, yPosition);
                    cs.lineTo(margin + tableWidth, yPosition);
                    cs.stroke();

                    float textY = yPosition - 15;

                    // 1. Tên SP
                    String name = item.getName() != null ? item.getName() : "";
                    if (name.length() > 35) name = name.substring(0, 32) + "..."; // Cắt tên nếu quá dài
                    drawText(cs, font, 10, currentX + 5, textY, name);
                    currentX += colWidths[0];

                    // 2. Phân loại
                    String variant = (item.getSizeLabel() != null ? item.getSizeLabel() : "") + " / " + (item.getColorLabel() != null ? item.getColorLabel() : "");
                    drawText(cs, font, 10, currentX + 5, textY, variant);
                    currentX += colWidths[1];

                    // 3. SL (Căn phải)
                    drawTextRight(cs, font, 10, currentX + colWidths[2] - 5, textY, String.valueOf(item.getQuantity()));
                    currentX += colWidths[2];

                    // 4. Đơn giá (Căn phải)
                    String price = currency.format(item.getUnitPrice()).replace("₫", ""); // Bỏ ký hiệu đ để clean hơn hoặc giữ tùy ý
                    drawTextRight(cs, font, 10, currentX + colWidths[3] - 5, textY, price);
                    currentX += colWidths[3];

                    // 5. Thành tiền (Căn phải)
                    String subtotal = currency.format(item.getSubtotal()).replace("₫", " đ");
                    drawTextRight(cs, font, 10, currentX + colWidths[4] - 5, textY, subtotal);

                    yPosition -= 25; // Chiều cao mỗi dòng
                }
            }

            // Kẻ đường kết thúc bảng đậm hơn
            cs.setStrokingColor(Color.BLACK);
            cs.moveTo(margin, yPosition);
            cs.lineTo(margin + tableWidth, yPosition);
            cs.stroke();

            // --- 5. Tổng tiền & Footer ---
            yPosition -= 30;

            // Box tổng tiền
            float totalBoxWidth = 200;
            float totalBoxX = margin + tableWidth - totalBoxWidth;

            cs.setNonStrokingColor(new Color(245, 245, 245));
            cs.addRect(totalBoxX, yPosition - 20, totalBoxWidth, 30);
            cs.fill();
            cs.setNonStrokingColor(Color.BLACK);

            drawText(cs, font, 12, totalBoxX + 10, yPosition - 12, "TỔNG CỘNG:");
            String totalStr = currency.format(req.getTotal()).replace("₫", " VND");
            cs.setNonStrokingColor(new Color(204, 0, 0)); // Màu đỏ cho tiền
            drawTextRight(cs, font, 14, margin + tableWidth - 10, yPosition - 12, totalStr);

            // Lời cảm ơn (Canh giữa trang)
            cs.setNonStrokingColor(Color.DARK_GRAY);
            yPosition -= 60;
            centerText(cs, font, 10, page.getMediaBox().getWidth(), yPosition, "Cảm ơn quý khách đã mua hàng tại Sports Shop!");
            centerText(cs, font, 10, page.getMediaBox().getWidth(), yPosition - 15, "Vui lòng giữ lại hóa đơn để đổi trả trong vòng 7 ngày.");

            cs.close();

            // Xuất file
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            doc.save(baos);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=HoaDon_" + req.getOrderId() + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());
        }
    }

    // --- Helper Methods để vẽ code gọn hơn ---

    // Vẽ text căn trái
    private void drawText(PDPageContentStream cs, PDType0Font font, float size, float x, float y, String text) throws IOException {
        cs.beginText();
        cs.setFont(font, size);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }

    // Vẽ text căn phải (Dùng cho số tiền)
    private void drawTextRight(PDPageContentStream cs, PDType0Font font, float size, float rightX, float y, String text) throws IOException {
        float width = font.getStringWidth(text) / 1000 * size;
        drawText(cs, font, size, rightX - width, y, text);
    }

    // Vẽ text căn giữa (Dùng cho Footer)
    private void centerText(PDPageContentStream cs, PDType0Font font, float size, float pageWidth, float y, String text) throws IOException {
        float width = font.getStringWidth(text) / 1000 * size;
        drawText(cs, font, size, (pageWidth - width) / 2, y, text);
    }
}
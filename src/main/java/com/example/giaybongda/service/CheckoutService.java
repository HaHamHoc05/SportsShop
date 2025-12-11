package com.example.giaybongda.service;

import com.example.giaybongda.model.*;
import com.example.giaybongda.repository.BillDetailRepository;
import com.example.giaybongda.repository.BillRepository;
import com.example.giaybongda.repository.ProductDetailRepository;
import com.example.giaybongda.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutService {
    @Autowired
    private BillRepository billRepo;
    @Autowired
    private BillDetailRepository billDetailRepo;
    @Autowired
    private ProductDetailRepository productDetailRepo;
    @Autowired
    private ProductRepository productRepo;

    @Transactional
    public Integer processOrder(HttpSession session, Integer makh, List<CartItem> items, long total,
                                String tenkh, String email, String sodienthoai, String diachi) {
        if (items == null || items.isEmpty()) throw new IllegalArgumentException("Giỏ hàng rỗng ");

        Bill bill = new Bill();
        bill.setMakh(makh);
        bill.setTenkh(tenkh);
        bill.setEmail(email);
        bill.setSodienthoai(sodienthoai);
        bill.setDiachi(diachi);
        bill.setNgaydat(LocalDate.now());
        bill.setTongtien(total);
        // set customer ( co the null)
//        bill.setTenkh(tenkh);
//        bill.setEmail(email);
//        bill.setSodienthoai(sodienthoai);
//        bill.setDiachi(diachi);


        bill = billRepo.save(bill);

        List<BillDetail> savedDetails = new ArrayList<>();

        for (CartItem it : items) {
            // tao chi tiet don hang
            BillDetail bd = new BillDetail();
            bd.setMasohd(bill.getMasohd());
            bd.setMahh(it.getProductId());
            bd.setSoluongmua(it.getQuantity());
            bd.setMausac(it.getColorLabel());
            bd.setSize(it.getSizeId());
            bd.setThanhtien(it.getSubtotal());
            bd.setTinhtrang(1);
            billDetailRepo.save(bd);
            savedDetails.add(bd);

            //cap nhat ton kho cua product
            ProductDetailID pdID = new ProductDetailID(it.getProductId(), it.getColorId(), it.getSizeId());
            Optional<ProductDetail> opt = productDetailRepo.findById(pdID);
            if (opt.isPresent()) {
                ProductDetail pd = opt.get();
                Integer stock = pd.getSoluongton() == null ? 0 : pd.getSoluongton();
                int newStock = stock - it.getQuantity();
                if (newStock < 0) newStock = 0;
                pd.setSoluongton(newStock);
                productDetailRepo.save(pd);

                // danh dau san pham het hang neu so luong sp =0
                if (newStock == 0) {
                    Optional<Product> otpP = productRepo.findById(it.getProductId());
                    if (otpP.isPresent()) {
                        Product p = otpP.get();
                        p.setTinhtrang(0);
                        productRepo.save(p);
                    }
                }
            }
        }
        session.removeAttribute("CART");
        return bill.getMasohd();
    }

}
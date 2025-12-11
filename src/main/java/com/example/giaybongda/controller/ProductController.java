package com.example.giaybongda.controller;

import com.example.giaybongda.model.*;
import com.example.giaybongda.repository.ProductRepository;
import com.example.giaybongda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public String allProducts(Model model ,@RequestParam(defaultValue = "0") int page) {
        int pageSize = 12; // mỗi trang hiển thị 8 sản phẩm
        Pageable pageable = PageRequest.of(page, pageSize);

        // Gọi repository phân trang
        Page<ProductWithPrice> productPage = productRepository.findAllWithPrice(pageable);


        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "products"; // trả về trang Thymeleaf

    }

    // new product detail
    @GetMapping("/product/{id}")
    public String product(@PathVariable("id") int id, Model model) {
        var product = productService.getById(id);
        if (product == null) {
            return "redirect:/"; // or show a 404 page
        }

        // --- Tăng số lượt xem ---
        product.setSoluotxem(product.getSoluotxem() + 1);
        productRepository.save(product);

        // --- Lấy danh sách biến thể ---
        var variants = productService.getVariantsByProductId(id);

        // Lấy giá chính (variant đầu tiên) ---
        Float mainPrice = 0f;
        if (variants != null && !variants.isEmpty()) {
            Float p = variants.get(0).getDongia();
            mainPrice = (p == null) ? 0f : p;
        }

        // Lấy danh sách màu
        List<Color> colors = variants.stream()
                .map(ProductDetail::getMausac)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        // --- Lấy danh sách size ---
        List<Size> sizes = variants.stream()
                .map(ProductDetail::getSizegiay)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        int totalStock = variants.stream().mapToInt(v ->(v.getSoluongton() == null ? 0 : v.getSoluongton())).sum();

        // --- Add vào model ---
        model.addAttribute("product", product);
        model.addAttribute("mainPrice", mainPrice);
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);
        model.addAttribute("variants", variants); // quan trọng
        model.addAttribute("totalStock", totalStock);

        return "productdetail";
    }

}
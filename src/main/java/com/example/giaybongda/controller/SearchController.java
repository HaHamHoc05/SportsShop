package com.example.giaybongda.controller;

import com.example.giaybongda.model.Product;
import com.example.giaybongda.model.ProductWithPrice;
import com.example.giaybongda.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchController {
    private final ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    // API AUTO SUGGEST
    @GetMapping("/suggest")
    @ResponseBody
    public List<ProductWithPrice> suggest(@RequestParam String q) {
        return productService.searchProducts(q)
                .stream()
                .limit(5)
                .toList();
    }

    // TRANG KẾT QUẢ TÌM KIẾM
    @GetMapping("/search")
    public String searchProducts(@RequestParam String q, Model model) {

        List<ProductWithPrice> results = productService.searchProducts(q);
        model.addAttribute("products", results);
        model.addAttribute("keyword", q);

        return "search";
    }
}


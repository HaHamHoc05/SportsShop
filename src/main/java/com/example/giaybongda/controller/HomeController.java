package com.example.giaybongda.controller;

import com.example.giaybongda.model.ProductWithPrice;
import com.example.giaybongda.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/" )
    public String homePage(Model model) {
        List<ProductWithPrice> sanPhamNoiBat = productRepository.findTopByViews(PageRequest.of(0, 4));
        List<ProductWithPrice> saleProducts = productRepository.findTopSaleProducts(PageRequest.of(0, 4));

        model.addAttribute("sanPhamNoiBat", sanPhamNoiBat);
        model.addAttribute("saleProducts", saleProducts);
        return "home";
    }


}

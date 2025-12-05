package com.example.giaybongda.controller;

import com.example.giaybongda.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalCartAdvice {

        private final CartService cartService;

        public GlobalCartAdvice(CartService cartService) {
            this.cartService = cartService;
        }
        @ModelAttribute
        public void addCartAttributes(Model model, HttpSession session) {
            int count = cartService.getItems(session).size();
            long total = cartService.getTotal(session);
            model.addAttribute("cartCount", count);
            model.addAttribute("cartTotal", total);
        }
}

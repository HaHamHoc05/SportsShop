package com.example.giaybongda.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("username")
    public String username(HttpSession session) {
        Object u = session.getAttribute("username");
        return u != null ? String.valueOf(u) : null;
    }
    @ModelAttribute("cartCount")
    public Integer cartCount(HttpSession session) {
        Object c = session.getAttribute("cartCount");
        if (c instanceof Integer) return (Integer) c;
        return 0;
    }
}

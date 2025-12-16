package com.example.giaybongda.controller;

import com.example.giaybongda.service.AdminService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        // Lấy dữ liệu thống kê từ service
        Long totalRevenue = adminService.getTotalRevenue();
        Long totalBills = adminService.getTotalBills();
        Long totalProducts = adminService.getTotalProducts();

        // Đưa dữ liệu vào model để Thymeleaf render
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalBills", totalBills);
        model.addAttribute("totalProducts", totalProducts);

        return "Admin/manage";
        }
}

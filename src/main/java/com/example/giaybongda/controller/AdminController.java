package com.example.giaybongda.controller;

import com.example.giaybongda.model.Bill;
import com.example.giaybongda.model.Customer;
import com.example.giaybongda.model.Product;
import com.example.giaybongda.service.AdminService;
import com.example.giaybongda.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;


@Controller

public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/admin")
    public String adminDashboard(HttpSession session, Model model,
                                 @RequestParam(name= "bPage", defaultValue = "0") int bPage,
                                 @RequestParam(name= "cPage", defaultValue = "0") int cPage,
                                 @RequestParam(name= "pPage", defaultValue = "0") int pPage,
                                 @RequestParam(name = "tab", required = false, defaultValue = "orders") String activeTab,
                                 @RequestParam(name = "filterType", required = false, defaultValue = "all") String filterType,
                                 @RequestParam(name = "dateInput", required = false) String dateInput, // Dùng cho theo ngày
                                 @RequestParam(name = "monthInput", required = false) Integer monthInput, // Dùng cho tháng
                                 @RequestParam(name = "quarterInput", required = false) Integer quarterInput, // Dùng cho quý
                                 @RequestParam(name = "yearInput", required = false) Integer yearInput, // Dùng cho năm
                                 Pageable pageable) {

        String role = (String) session.getAttribute("role");

        // chưa đăng nhập hoặc không phải admin cho về trang chủ
        if (role == null || !role.equals("ADMIN")) {
            return "redirect:/";
        }
        int pageSize = 10;

        // lấy danh sách khách hàng
        Page<Customer> customerPage = adminService.findAllCustomers(cPage,pageSize);
        // lấy danh sách hóa đơn
        Page<Bill> billPage = adminService.getAllBills(bPage,pageSize);
        // lấy danh sách sản phẩm
        Page<Product> productPage = adminService.findAllProducts(pPage,pageSize);

        Long totalRevenue;
        Long totalBills;
        Long totalProducts = adminService.getTotalProducts();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        boolean isFiltered = false;

        int currentYear = (yearInput != null) ? yearInput : LocalDate.now().getYear();

        try {
            switch (filterType) {
                case "date":
                    if (dateInput != null && !dateInput.isEmpty()) {
                        startDate = LocalDate.parse(dateInput);
                        endDate = startDate;
                        isFiltered = true;
                    }
                    break;
                case "month":
                    if (monthInput != null) {
                        startDate = LocalDate.of(currentYear, monthInput, 1);
                        endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
                        isFiltered = true;
                    }
                    break;
                case "quarter":
                    if (quarterInput != null) {
                        int startMonth = (quarterInput - 1) * 3 + 1;
                        startDate = LocalDate.of(currentYear, startMonth, 1);
                        endDate = startDate.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
                        isFiltered = true;
                    }
                    break;
                case "year":
                    startDate = LocalDate.of(currentYear, 1, 1);
                    endDate = LocalDate.of(currentYear, 12, 31);
                    isFiltered = true;
                    break;
                default:
                    isFiltered = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isFiltered = false;
        }

        if (isFiltered) {
            totalRevenue = adminService.getTotalRevenuesByDateRange(startDate, endDate);
            totalBills = adminService.getBillsCountByDateRange(startDate, endDate);
        } else {
            totalRevenue = adminService.getTotalRevenue();
            totalBills = adminService.getTotalBills();
        }



        model.addAttribute("customerPage", customerPage);
        model.addAttribute("billPage", billPage);
        model.addAttribute("productPage", productPage);

        model.addAttribute("currentbPage", bPage);
        model.addAttribute("currentcPage", cPage);
        model.addAttribute("currentpPage", pPage);

        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalBills", totalBills);
        model.addAttribute("totalProducts", totalProducts);

        model.addAttribute("activeTab", activeTab);

        model.addAttribute("selectedFilter", filterType);
        model.addAttribute("selectedDate", dateInput);
        model.addAttribute("selectedMonth", monthInput);
        model.addAttribute("selectedQuarter", quarterInput);
        model.addAttribute("selectedYear", yearInput != null ? yearInput : LocalDate.now().getYear());

        return "Admin/manage";
        }
}

package com.example.giaybongda.controller;

import com.example.giaybongda.model.Customer;
import com.example.giaybongda.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("customer")) {
            model.addAttribute("customer", new Customer());
        }
        return "register";
    }
    //khi người dùng nhấn nút đăng ký thì controller nhận yêu cầu POST tới /register
    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("customer") Customer customer,
                                      BindingResult bindingResult,
                                      Model model) {
        if (!bindingResult.hasErrors()) {
            if (customer.getConfirmPassword()==null || customer.getConfirmPassword().isBlank()) {
                bindingResult.rejectValue("confirmPassword", "confirm.empty", "Vui lòng nhập xác nhận mật khẩu");
            } else if (customer.getMatkhau() == null || !customer.getMatkhau().equals(customer.getConfirmPassword())) {
                bindingResult.rejectValue("confirmPassword", "password.mismatch", "Mật khẩu xác nhận không khớp");
            }
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            customerService.register(customer);
            //khi lưu có trường hợp username, email bị trùng, service sẽ ném IllegalArgumentException

        }catch (IllegalArgumentException ex){
            String msg=ex.getMessage();
            if("username_exists".equals(msg)) {
                bindingResult.rejectValue("username","username.exists", "Tên đăng nhập đã tồn tại");
                return "register";
            }
            else if("email_exists".equals(msg)) {
                bindingResult.rejectValue("email","email.exists", "Email đã tồn tại");
                return "register";
            }
            else {
                // other errors
                model.addAttribute("error", "Đăng ký thất bại do lỗi hệ thống. Vui lòng thử lại sau.");
                return "register";
            }

        }

        return "redirect:/";
    }
}



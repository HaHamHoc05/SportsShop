package com.example.giaybongda.controller;

import com.example.giaybongda.model.Customer;
import com.example.giaybongda.service.CustomerService;
import com.example.giaybongda.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private CustomerService customerService;


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam(required = false) String redirect,
                          Model model,
                          HttpSession session) {
        Optional<Customer> opt = customerService.authenticate(username, password);
        if (opt.isPresent()) {
            Customer cs = opt.get();
            session.setAttribute("username", cs.getUsername());
            session.setAttribute("userid", cs.getMakh());
            // Nếu có redirect  quay lại đúng trang trước đó
            if (redirect != null && !redirect.isEmpty()) {
                return "redirect:" + redirect;
            }

            return "redirect:/"; // Mặc định
        }

        model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
        model.addAttribute("username", null); // để fragment header nhận null

        return "login";
    }

    // Lấy GET logout (link)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // Nhấn vào logout thì dùng invalidate session để đăng xuất
    // invalidate(): Hủy toàn bộ session (đăng xuất, hết hạn)
    @PostMapping("/logout")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

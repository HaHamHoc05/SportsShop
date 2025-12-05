package com.example.giaybongda.controller;

import com.example.giaybongda.model.CartItem;
import com.example.giaybongda.model.ProductDetail;
import com.example.giaybongda.service.CartService;
import com.example.giaybongda.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {
    // controller goi den service de xu ly cart
    private final CartService cartService;
    private final ProductService productService;
    public CartController(CartService cartSevice, ProductService productService) {
        this.cartService = cartSevice;
        this.productService = productService;
    }
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute("items",cartService.getItems(session));
        model.addAttribute("total",cartService.getTotal(session));
        return "cart";
    }
    @PostMapping("/cart/add")
    public String addToCart(HttpSession session,@RequestParam Integer productId,
                            @RequestParam String name,
                            @RequestParam String image,
                            @RequestParam(required = false) Integer sizeId,
                            @RequestParam(required = false) Integer colorId,
                            @RequestParam(required = false) Integer quantity,
                            RedirectAttributes redirect) {
        if (sizeId == null || colorId == null ) {
            redirect.addAttribute("error", "Vui lòng chọn màu và size");
            return "redirect:/product/" +productId;
        }

        int qty = (quantity == null || quantity <= 0) ? 1 : quantity;

        ProductDetail variant = productService.getvariant(productId, sizeId, colorId);
        if (variant == null) {
            redirect.addFlashAttribute("error", "Biến thể không tồn tại");
            return "redirect:/product/" +productId;
        }

        long unitPriceLong = 0L;
        Float p = variant.getDongia();
        if (p != null) unitPriceLong = Math.round(p);

        String sizeLabel = variant.getSizegiay() != null ? variant.getSizegiay().getSize() : "";
        String colorLabel = variant.getMausac() != null ? variant.getMausac().getMausac() : "";

        String key = productId + "_" + sizeId + "_" + colorId;
        CartItem item = new CartItem(key, productId, name, image, sizeId, sizeLabel, colorId, colorLabel, unitPriceLong, qty);
        cartService.addItem(session, item);
        redirect.addFlashAttribute("msg", "Đã thêm vào giỏ");
        return "redirect:/cart";
    }
    @PostMapping("/cart/increment")
    public String increment(HttpSession session, @RequestParam String key) {
        cartService.increment(session, key);
        return "redirect:/cart";
    }

    @PostMapping("/cart/decrement")
    public String decrement(HttpSession session, @RequestParam String key) {
        cartService.decrement(session, key);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String remove(HttpSession session, @RequestParam String key) {
        cartService.remove(session, key);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clear(HttpSession session) {
        cartService.clear(session);
        return "redirect:/cart";
    }
}

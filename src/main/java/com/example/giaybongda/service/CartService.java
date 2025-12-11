package com.example.giaybongda.service;

import com.example.giaybongda.model.CartItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {
    //key dung de luu ban do gio hang vao httpss
    private static  final String CART_SESSION_KEY = "CART";

    @SuppressWarnings("unchecked")
    //map giup tra cuu them xoa tang giam so luong nhanh chong theo key
    private Map<String, CartItem> getCartMap(HttpSession session) {
        // lay object trong session theo key cart_session_key
        // neu object do la map -> cast ve map<string, cartitem> va tra ve
        Object obj = session.getAttribute(CART_SESSION_KEY);
        if (obj instanceof Map) return (Map<String, CartItem>) obj;
        //gio hang dung linkedhashmap de hien thi dung thu tu UI
        Map<String, CartItem> map = new LinkedHashMap<>();
        session.setAttribute(CART_SESSION_KEY, map);
        return map;
    }
    // phuong thuo lay danh sach gio hang
    // lay danh sach cartitem de render ra man hinh
    // lay tat ca cac gia tri value() trong map > moi cai la mot cart item
    // boc vao arraylist tra ve list
    public List<CartItem> getItems(HttpSession session) {
        return new ArrayList<>(getCartMap(session).values());
    }

    public void addItem(HttpSession session, CartItem item) {
        Map<String, CartItem> map = getCartMap(session);
        CartItem existing = map.get(item.getKey());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        } else {
            map.put(item.getKey(), item);
        }
        session.setAttribute(CART_SESSION_KEY, map);
    }

    public void increment(HttpSession session, String key) {
        Map<String, CartItem> map = getCartMap(session);
        CartItem it = map.get(key);
        if(it != null) {
            it.setQuantity(it.getQuantity() + 1);
        }
    }
    public void decrement(HttpSession session, String key) {
        Map<String, CartItem> map = getCartMap(session);
        CartItem it = map.get(key);
        if(it != null) {
            it.setQuantity(it.getQuantity() - 1);
            if(it.getQuantity() <= 0) map.remove(key);
        }
    }

    public void remove(HttpSession session, String key) {
        Map<String, CartItem> map = getCartMap(session);
        map.remove(key);
    }
    public void clear(HttpSession session) {
        Map<String, CartItem> map = getCartMap(session);
        map.clear();
    }

    // lay tong tien trong gio hang trong session
    public long getTotal(HttpSession session) {
        return getCartMap(session).values().stream().mapToLong(CartItem::getSubtotal).sum();
    }


}

package com.example.giaybongda.service;

import com.example.giaybongda.model.Customer;
import com.example.giaybongda.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repoCustomer;
    @Autowired
    private PasswordEncoder passwordEncoder;
    // phương thức lưu đối tượng KH
    public Customer register(Customer customer){
        customer.setMatkhau(passwordEncoder.encode(customer.getMatkhau()));
        return repoCustomer.save(customer);
    }
    // dang nhap
    public Optional<Customer> authenticate(String username, String rawPassword){
        if(username == null || rawPassword == null) return Optional.empty();
        Optional<Customer> opt = repoCustomer.findByUsername(username);
        if(opt.isPresent()) {
            Customer customer = opt.get();
            if(passwordEncoder.matches(rawPassword,customer.getMatkhau())) return  Optional.of(customer);
        }
        return Optional.empty();
    }

}

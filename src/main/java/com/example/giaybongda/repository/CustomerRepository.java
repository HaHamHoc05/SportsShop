package com.example.giaybongda.repository;

import com.example.giaybongda.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    // danh sách lưới
    Optional<Customer> findByUsername(String username);
}

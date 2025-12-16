package com.example.giaybongda.service;

import com.example.giaybongda.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    public Long getTotalRevenue() {
        Long total = adminRepo.getTotalRevenue();
        return total != null ? total : 0L;
    }

    public Long getTotalBills() {
        Long totalBills = adminRepo.getTotalBills();
        return totalBills != null ? totalBills : 0L;
    }

    public Long getTotalProducts() {
        Long totalProducts = adminRepo.getTotalProducts();
        return totalProducts != null ? totalProducts : 0L;
    }
}

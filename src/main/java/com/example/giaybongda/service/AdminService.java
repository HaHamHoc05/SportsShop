package com.example.giaybongda.service;

import com.example.giaybongda.model.Bill;
import com.example.giaybongda.repository.AdminRepository;
import com.example.giaybongda.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private BillRepository billRepo;

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

    public Long getTotalRevenuesByDateRange(LocalDate start, LocalDate end) {
        Long total = adminRepo.calculateRevenue(start, end);
        return total != null ? total : 0L;
    }

    public Long getBillsCountByDateRange(LocalDate start, LocalDate end) {
        Long count = adminRepo.countBills(start, end);
        return count != null ? count : 0L;
    }

    // lấy danh sách hóa đơn phân trang và sắp xếp theo mahd
    public Page<Bill> getAllBills(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("masohd").descending());
        return billRepo.findAll(pageable);
    }
}

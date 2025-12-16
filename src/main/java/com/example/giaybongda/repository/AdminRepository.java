package com.example.giaybongda.repository;

import com.example.giaybongda.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Bill,Long> {
    @Query("SELECT SUM(b.tongtien) FROM Bill b")
    Long getTotalRevenue();

    @Query("SELECT COUNT(b) FROM Bill b")
    Long getTotalBills();

    @Query("SELECT COUNT(p.mahh) FROM Product p")
    Long getTotalProducts();
}

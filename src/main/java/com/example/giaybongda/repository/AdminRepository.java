package com.example.giaybongda.repository;

import com.example.giaybongda.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface AdminRepository extends JpaRepository<Bill,Long> {
    @Query("SELECT SUM(b.tongtien) FROM Bill b")
    Long getTotalRevenue();

    @Query("SELECT COUNT(b) FROM Bill b")
    Long getTotalBills();

    @Query("SELECT COUNT(p.mahh) FROM Product p")
    Long getTotalProducts();

    // tong doanh thu theo thoi gian
    @Query("SELECT SUM(b.tongtien) FROM Bill b WHERE b.ngaydat BETWEEN :startDate AND :endDate")
    Long calculateRevenue(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // đếm số đơn hàng theo thoi gian
    @Query("SELECT COUNT(b) FROM Bill b WHERE b.ngaydat BETWEEN :startDate AND :endDate")
    Long countBills(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



}

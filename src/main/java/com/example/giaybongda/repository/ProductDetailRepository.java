package com.example.giaybongda.repository;

import com.example.giaybongda.model.ProductDetail;
import com.example.giaybongda.model.ProductDetailID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, ProductDetailID> {
    @Query("SELECT c from ProductDetail c JOIN FETCH c.mausac JOIN FETCH c.sizegiay WHERE c.idhanghoa = :idhanghoa")
    List<ProductDetail> findByIDProductWithDetail(@Param("idhanghoa") int idhanghoa);
}
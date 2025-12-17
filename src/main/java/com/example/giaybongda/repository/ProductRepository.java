package com.example.giaybongda.repository;

import com.example.giaybongda.model.Product;
import com.example.giaybongda.model.ProductWithPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Page<Product> findAll(Pageable pageable);

    // 4 sản phẩm nổi bật nhiều lượt xem nhất, lấy giá thấp nhất từ chi tiết
    @Query("SELECT new com.example.giaybongda.model.ProductWithPrice(p.mahh, p.tenhh, p.hinh, pd.dongia) " +
            "FROM Product p JOIN p.chiTiet pd " +
            "ORDER BY p.soluotxem DESC")
    List<ProductWithPrice> findTopByViews(Pageable pageable);

    // 4 sản phẩm giảm giá (giamgia > 0), lấy giá thấp nhất
    @Query("SELECT new com.example.giaybongda.model.ProductWithPrice(p.mahh, p.tenhh, p.hinh, MIN(pd.dongia)) " +
            "FROM Product p JOIN p.chiTiet pd " +
            "WHERE p.giamgia > 0 " +
            "GROUP BY p.mahh, p.tenhh, p.hinh, p.giamgia " +
            "ORDER BY p.giamgia DESC")
    List<ProductWithPrice> findTopSaleProducts(Pageable pageable);
//phan trang
    @Query("SELECT new com.example.giaybongda.model.ProductWithPrice(p.mahh, p.tenhh, p.hinh, MIN(pd.dongia)) " +
            "FROM Product p JOIN p.chiTiet pd " +
            "WHERE p.tinhtrang = :status " +
            "GROUP BY p.mahh, p.tenhh, p.hinh")
    Page<ProductWithPrice> findAllWithPriceActive(@Param("status") int status, Pageable pageable);


    @Query("SELECT new com.example.giaybongda.model.ProductWithPrice(p.mahh, p.tenhh, p.hinh, min(pd.dongia)) " +
            "FROM Product p JOIN p.chiTiet pd " +
            "WHERE LOWER(p.tenhh) LIKE LOWER(CONCAT('%', :keyword, '%'))\n" +
            "GROUP BY p.mahh, p.tenhh, p.hinh")
    List<ProductWithPrice> findByTenhhContainingIgnoreCase(String keyword);



}

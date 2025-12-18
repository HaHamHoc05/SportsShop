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

    // 1. Lọc sản phẩm nổi bật
    @Query("SELECT new com.example.giaybongda.model.ProductWithPrice(p.mahh, p.tenhh, p.hinh, MIN(pd.dongia)) " +
            "FROM Product p JOIN p.chiTiet pd " +
            "WHERE p.tinhtrang = 1 " + // Thêm dòng này
            "GROUP BY p.mahh, p.tenhh, p.hinh, p.soluotxem " +
            "ORDER BY p.soluotxem DESC")
    List<ProductWithPrice> findTopByViews(Pageable pageable);

    // 2. Lọc sản phẩm giảm giá
    @Query("SELECT new com.example.giaybongda.model.ProductWithPrice(p.mahh, p.tenhh, p.hinh, MIN(pd.dongia)) " +
            "FROM Product p JOIN p.chiTiet pd " +
            "WHERE p.giamgia > 0 AND p.tinhtrang = 1 " + // Thêm p.tinhtrang = 1
            "GROUP BY p.mahh, p.tenhh, p.hinh, p.giamgia " +
            "ORDER BY p.giamgia DESC")
    List<ProductWithPrice> findTopSaleProducts(Pageable pageable);

    // 3. Lọc trang danh sách sản phẩm (đã có trong code của bạn nhưng cần kiểm tra tham số)
    @Query("SELECT new com.example.giaybongda.model.ProductWithPrice(p.mahh, p.tenhh, p.hinh, MIN(pd.dongia)) " +
            "FROM Product p JOIN p.chiTiet pd " +
            "WHERE p.tinhtrang = 1 " +
            "GROUP BY p.mahh, p.tenhh, p.hinh")
    Page<ProductWithPrice> findAllWithPriceActive(Pageable pageable);

    // 4. Lọc kết quả tìm kiếm
    @Query("SELECT new com.example.giaybongda.model.ProductWithPrice(p.mahh, p.tenhh, p.hinh, min(pd.dongia)) " +
            "FROM Product p JOIN p.chiTiet pd " +
            "WHERE LOWER(p.tenhh) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND p.tinhtrang = 1 " + // Thêm dòng này
            "GROUP BY p.mahh, p.tenhh, p.hinh")
    List<ProductWithPrice> findByTenhhContainingIgnoreCase(@Param("keyword") String keyword, int i);
}

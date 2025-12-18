package com.example.giaybongda.service;

import com.example.giaybongda.model.Product;
import com.example.giaybongda.model.ProductDetail;
import com.example.giaybongda.model.ProductWithPrice;
import com.example.giaybongda.repository.ProductDetailRepository;
import com.example.giaybongda.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private ProductDetailRepository detailRepo;

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    public void deleteById(int id) {
        productRepo.deleteById(id);
    }

    // New: get product by id
    public Product getById(int id) {
        Optional<Product> o = productRepo.findById(id);
        return o.orElse(null);
      }

    public void save(Product product) {
        productRepo.save(product);
    }
    // lấy màu sắc theo id
    public List<ProductDetail> getVariantsByProductId(int mahh)
    {
        return detailRepo.findByIDProductWithDetail(mahh);
    }

    public ProductDetail getvariant(int mahh, int sizeId, int colorId) {
        List<ProductDetail> list = detailRepo.findByIDProductWithDetail(mahh);
        if (list == null) return null;
        for (ProductDetail v : list) {
            if (v.getIdsize() == sizeId && v.getIdmau()==colorId) return v;
        }
        return null;
    }
    public List<ProductWithPrice> searchProducts(String keyword) {
        return productRepo.findByTenhhContainingIgnoreCase(keyword,1);
    }

    public Page<ProductWithPrice> getAllActiveProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepo.findAllWithPriceActive(pageable);
    }
}
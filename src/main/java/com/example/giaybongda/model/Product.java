package com.example.giaybongda.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="hanghoa")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mahh;

    private String tenhh;
    private Float giamgia;
    private String hinh;
    private Integer maloai;
    private Integer mathuonghieu;
    private Boolean dacbiet;
    private Integer soluotxem;
    private LocalDate ngaylap;
    private String mota;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductDetail> chiTiet;



}

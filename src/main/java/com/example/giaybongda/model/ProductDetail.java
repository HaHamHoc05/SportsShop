package com.example.giaybongda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cthanghoa")
@IdClass(ProductDetailID.class)
public class ProductDetail {
    @Id
    @Column(name = "idhanghoa")
    private int idhanghoa;
    @Id
    @Column(name = "idmau")
    private int idmau;
    @Id
    @Column(name = "idsize")
    private int idsize;
    @Column(name = "dongia")
    private Float dongia;
    @Column(name = "soluongton")
    private Integer soluongton;

    // Map relationships to other entities (read-only here using insertable=false, updatable=false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idhanghoa", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmau", insertable = false, updatable = false)
    private Color mausac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsize", insertable = false, updatable = false)
    private Size sizegiay;
}

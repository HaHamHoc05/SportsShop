package com.example.giaybongda.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "cthoadon")
@IdClass(BillDetailID.class)
public class BillDetail implements Serializable {
    @Id
    @Column(name = "masohd")
    private Integer masohd;

    @Id
    @Column(name = "mahh")
    private Integer mahh;

    @Column(name = "soluongmua")
    private Integer soluongmua;

    @Column(name = "mausac")
    private String mausac;

    @Column(name = "size")
    private Integer size;

    @Column(name = "thanhtien")
    private Long thanhtien;

    @Column(name = "tinhtrang")
    private Integer tinhtrang;
}
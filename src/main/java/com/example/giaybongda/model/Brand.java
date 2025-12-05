package com.example.giaybongda.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "thuonghieu")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mathuonghieu;

    private String tenthuonghieu;

    //@OneToMany(mappedBy = "thuonghieu")
    //private List<Product> products;
}

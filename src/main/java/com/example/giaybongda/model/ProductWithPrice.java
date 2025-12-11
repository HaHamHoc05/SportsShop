package com.example.giaybongda.model;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductWithPrice {
    private Integer mahh;
    private String tenhh;
    private String hinh;
    private Float dongia;


}

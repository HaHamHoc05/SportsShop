package com.example.giaybongda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "mausac")
public class Color {
    @Id
    @Column(name = "idmau")
    private Integer idmau;

    @Column(name = "mausac")
    private String mausac;
}

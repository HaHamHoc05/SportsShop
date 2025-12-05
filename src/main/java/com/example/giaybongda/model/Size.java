package com.example.giaybongda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "sizegiay")
public class Size {
    @Id
    @Column(name = "idsize")
    private Integer idsize;

    @Column(name ="size")
    private String size;


}

package com.example.giaybongda.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name="hoadon")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer masohd;

    private Integer makh;

    private LocalDate ngaydat;

    private Long tongtien;

    private String tenkh;
    private String email;
    private String sodienthoai;
    private String diachi;
}

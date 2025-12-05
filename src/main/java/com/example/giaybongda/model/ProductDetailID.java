package com.example.giaybongda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailID implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idhanghoa;
    private int idmau;
    private int idsize;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetailID that = (ProductDetailID) o;
        return idhanghoa == that.idhanghoa && idmau == that.idmau
                && idsize == that.idsize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idhanghoa, idmau, idsize);
    }
}

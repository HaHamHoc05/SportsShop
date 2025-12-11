package com.example.giaybongda.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
        * Composite id class for CTHangHoa (idhanghoa, idmau, idsize)
 * implements Serializable → cho phép Hibernate tuần tự hóa khóa chính phức hợp.
        *
        * private static final long serialVersionUID = 1L;
 * → là mã phiên bản để đảm bảo tính tương thích khi class thay đổi giữa các phiên bản (rất quan trọng khi lưu cache hoặc truyền qua mạng).
        */

public class BillDetailID implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer masohd;
    private Integer mahh;

    public BillDetailID() {}
    public BillDetailID(Integer masohd, Integer mahh) { this.masohd = masohd; this.mahh = mahh; }

    public Integer getMasohd() { return masohd; }
    public void setMasohd(Integer masohd) { this.masohd = masohd; }
    public Integer getMahh() { return mahh; }
    public void setMahh(Integer mahh) { this.mahh = mahh; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDetailID that = (BillDetailID) o;
        return Objects.equals(masohd, that.masohd) && Objects.equals(mahh, that.mahh);
    }

    @Override
    public int hashCode() { return Objects.hash(masohd, mahh); }
}
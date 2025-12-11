package com.example.giaybongda.repository;

import com.example.giaybongda.model.BillDetail;
import com.example.giaybongda.model.BillDetailID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, BillDetailID> {
}

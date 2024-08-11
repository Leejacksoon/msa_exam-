package com.sparta.msa_exam.order.repository;


import com.sparta.msa_exam.order.entity.OrderMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMappingRepository extends JpaRepository<OrderMapping,Long> {
}

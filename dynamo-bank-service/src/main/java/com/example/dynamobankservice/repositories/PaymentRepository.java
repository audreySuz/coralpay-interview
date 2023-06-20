package com.example.dynamobankservice.repositories;

import com.example.dynamobankservice.models.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentRequest, Long> {
}

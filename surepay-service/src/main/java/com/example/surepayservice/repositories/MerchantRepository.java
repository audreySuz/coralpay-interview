package com.example.surepayservice.repositories;

import com.example.surepayservice.models.Customer;
import com.example.surepayservice.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {
    Optional<Merchant> findMerchantByMerchantCode(String merchantCode);
}

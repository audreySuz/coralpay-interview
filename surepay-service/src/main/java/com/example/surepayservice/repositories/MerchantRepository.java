package com.example.surepayservice.repositories;

import com.example.surepayservice.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {
    Optional<Merchant> findByMerchantCode(String merchantCode);
    Optional<Merchant> findByEmailOrPhone(String email, String phone);
    @Query(value = "SELECT id FROM merchant ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Long getLastInsertedMerchantId();
}

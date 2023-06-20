package com.example.surepayservice.repositories;

import com.example.surepayservice.models.VirtualAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualAccountRepository extends JpaRepository<VirtualAccount,Long> {
}

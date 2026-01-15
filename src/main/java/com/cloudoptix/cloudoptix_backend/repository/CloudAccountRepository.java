package com.cloudoptix.cloudoptix_backend.repository;

import com.cloudoptix.cloudoptix_backend.entity.CloudAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudAccountRepository extends JpaRepository<CloudAccount, Long> {
}

package com.cloudoptix.cloudoptix_backend.service;

import com.cloudoptix.cloudoptix_backend.entity.CloudAccount;
import com.cloudoptix.cloudoptix_backend.repository.CloudAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CloudAccountService {

    private final CloudAccountRepository repository;

    public CloudAccount addAccount(CloudAccount account, String adminEmail) {
        account.setCreatedBy(adminEmail);
        account.setCreatedAt(LocalDateTime.now());
        return repository.save(account);
    }

    public List<CloudAccount> getAllAccounts() {
        return repository.findAll();
    }
}

package com.cloudoptix.cloudoptix_backend.controller;

import com.cloudoptix.cloudoptix_backend.entity.CloudAccount;
import com.cloudoptix.cloudoptix_backend.service.CloudAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cloud-accounts")
@RequiredArgsConstructor
public class CloudAccountController {

    private final CloudAccountService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CloudAccount addAccount(@RequestBody CloudAccount account,
                                   Authentication authentication) {
        return service.addAccount(account, authentication.getName());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<CloudAccount> getAllAccounts() {
        return service.getAllAccounts();
    }
}

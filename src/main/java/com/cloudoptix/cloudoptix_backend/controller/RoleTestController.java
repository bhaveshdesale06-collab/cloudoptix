package com.cloudoptix.cloudoptix_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleTestController {

    @GetMapping("/user/test")
    @PreAuthorize("hasRole('USER')")
    public String userEndpoint() {
        return "USER endpoint accessed";
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "ADMIN endpoint accessed";
    }
}

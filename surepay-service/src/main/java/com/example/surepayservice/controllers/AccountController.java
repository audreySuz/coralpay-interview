package com.example.surepayservice.controllers;


import com.example.surepayservice.domains.AppResponse;
import com.example.surepayservice.dtos.requests.AccountRequest;
import com.example.surepayservice.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/surepay")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/generateAccount")
    public ResponseEntity<AppResponse> createPaymentRequest(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.ok().body(accountService.generateAccount(accountRequest));
    }

}

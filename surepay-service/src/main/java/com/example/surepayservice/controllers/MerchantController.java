package com.example.surepayservice.controllers;

import com.example.surepayservice.domains.AppResponse;
import com.example.surepayservice.dtos.requests.CreateMerchantRequestDto;
import com.example.surepayservice.services.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping("")
    public ResponseEntity<AppResponse> createMerchant(@RequestBody CreateMerchantRequestDto requestDto){
        AppResponse response = merchantService.registerMerchant(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

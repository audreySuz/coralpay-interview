package com.example.surepayservice.services;

import com.example.surepayservice.domains.AppResponse;
import com.example.surepayservice.dtos.requests.CreateMerchantRequestDto;

public interface MerchantService {
    AppResponse registerMerchant(CreateMerchantRequestDto requestDto);
}

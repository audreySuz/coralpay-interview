package com.example.surepayservice.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantResponseDto {

    private  String merchantCode;
    private  String name;
    private  String email;
    private  String phone;
    private  String apiSecret;
}

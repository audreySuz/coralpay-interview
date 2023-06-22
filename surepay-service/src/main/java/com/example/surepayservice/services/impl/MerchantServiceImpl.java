package com.example.surepayservice.services.impl;

import com.example.surepayservice.domains.AppResponse;
import com.example.surepayservice.dtos.requests.CreateMerchantRequestDto;
import com.example.surepayservice.dtos.responses.MerchantResponseDto;
import com.example.surepayservice.exceptions.ValidatorException;
import com.example.surepayservice.models.Merchant;
import com.example.surepayservice.repositories.MerchantRepository;
import com.example.surepayservice.services.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    private final PasswordEncoder passwordEncoder;
    private final MerchantRepository merchantRepository;
    @Override
    public AppResponse registerMerchant(CreateMerchantRequestDto requestDto) {
        merchantRepository.findByEmailOrPhone(requestDto.getEmail(),requestDto.getPhone())
                .ifPresent(merchant -> {
                    throw new ValidatorException("Merchant already exists");
                });
        String merchantCode =generateMerchantCode(requestDto);
        String apiSecret = generateApiSecret(requestDto);

        Merchant merchant = Merchant.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .merchantCode(merchantCode)
                .accountType(requestDto.getAccountType())
                .apiSecret(passwordEncoder.encode(apiSecret))//hash the api secret
                .build();
        merchantRepository.save(merchant);
        String message = "Merchant created successfully, merchant code is "+merchantCode+" and api secret is "+apiSecret
                + " please store the api secret safely as it will not be shown again";
        MerchantResponseDto response = buildMerchantResponseDto(merchantCode, apiSecret, merchant);
        return AppResponse.ok(message,response);
    }

    private static MerchantResponseDto buildMerchantResponseDto(String merchantCode, String apiSecret, Merchant merchant) {
        return MerchantResponseDto.builder()
                .merchantCode(merchantCode)
                .name(merchant.getName())
                .email(merchant.getEmail())
                .phone(merchant.getPhone())
                .apiSecret(apiSecret)
                .build();
    }

    private String generateApiSecret(CreateMerchantRequestDto requestDto) {
        return java.util.UUID.randomUUID().toString();
    }

    private String generateMerchantCode(CreateMerchantRequestDto requestDto) {
        long count = merchantRepository.getLastInsertedMerchantId()+1;
        String name = requestDto.getName();
        return name.substring(0,3).toUpperCase()+count;
    }
}

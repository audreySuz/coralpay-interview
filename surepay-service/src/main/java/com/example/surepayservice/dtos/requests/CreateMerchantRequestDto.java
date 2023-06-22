package com.example.surepayservice.dtos.requests;

import com.example.surepayservice.models.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class CreateMerchantRequestDto {


    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotEmpty(message = "merchantCode cannot be empty")
    private String email;

    @NotEmpty(message = "merchantCode cannot be empty")
    private String phone;

    @NotNull(message = "accountType cannot be empty")
    @Schema(description = "The type of account the merchant desires to be created for their customers(this defines the limit for the account)")
    private AccountType accountType;

}

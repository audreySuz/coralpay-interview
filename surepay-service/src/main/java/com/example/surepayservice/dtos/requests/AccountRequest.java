package com.example.surepayservice.dtos.requests;

import com.example.surepayservice.enums.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequest {
        private Bank preferredBank;
        private String amount;
        private String firstName;
        private String lastName;
        private String merchantCode;
        private String phone;
        private String email;
        private String callBackUrl;
        private PaymentType paymentType;
}

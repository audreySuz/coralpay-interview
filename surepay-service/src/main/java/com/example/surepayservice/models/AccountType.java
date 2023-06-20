package com.example.surepayservice.models;


import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AccountType implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String currency;
    private String expiryDateTime;

    @NotNull
    @DecimalMax("1.0")
    private Float collectionFeeShare;
    private boolean allowPartialPayments;
    private boolean allowOverPayments;
    private String paymentType;
}

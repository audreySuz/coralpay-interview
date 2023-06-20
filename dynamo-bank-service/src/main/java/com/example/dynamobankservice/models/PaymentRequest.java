package com.example.dynamobankservice.models;


import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class PaymentRequest implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String referenceNumber;

    @NotNull
    private Float amount;

    @NotBlank
    private String currency;

    @ManyToOne(cascade = CascadeType.ALL)
    private Payer payer;

    @ManyToOne(cascade = CascadeType.ALL)
    private Payee payee;

    private LocalDateTime expiryDateTime;

    @NotNull
    @DecimalMax("1.0")
    private Float payerCollectionFeeShare;

    @NotNull
    @DecimalMax("1.0")
    private Float payeeCollectionFeeShare;

    private boolean allowPartialPayments;

    private boolean allowOverPayments;

    private String callBackUrl;

    private String paymentType;

    private String accountNumber;
}

package com.example.surepayservice.models;

import com.example.surepayservice.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VirtualAccount implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String accountID;
    private String  accountNumber;
    private String refference;
    private LocalDateTime deletedDate;
    private LocalDateTime createdDate;
    private String createdBy;
    private Status status;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
}

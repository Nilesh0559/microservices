package com.project.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Accounts extends BaseEntity {

    private Long customerId;
    @Id
    private Long accountNumber;
    private String accountType;
    private String branchAddress;

}

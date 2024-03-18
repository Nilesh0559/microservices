package com.project.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "Account", description = "Schema holder for Account information")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountsDto {

    @Schema(description = "Account number for BankApp", example = "1234567890")
    @NotEmpty(message = "AccountNumber can not be null or empty")
    @Pattern(regexp = "$|[0-9]{10}", message = "Mobile number must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Account type for BankApp", example = "Savings")
    @NotEmpty(message = "AccountType can not be null or empty")
    private String accountType;

    @Schema(description = "BankApp branch address", example = "123, New York")
    @NotEmpty(message = "BranchAddress can not be null or empty")
    private String branchAddress;
}

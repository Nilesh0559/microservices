package com.project.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "Customer", description = "Schema holder for Customer and Account information")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDto {

    @Schema(description = "Name of customer")
    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 & 30")
    private String name;

    @Schema(description = "Email address of customer", example = "nilesh@gmail.com")
    @NotEmpty(message = "Email address can not be null or empty")
    @Email(message = "Email address should be valid value")
    private String email;

    @Schema(description = "Mobile number of customer", example = "1234567890")
    @Pattern(regexp = "$|[0-9]{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Account details of customer")
    private AccountsDto accountsDto;
}

package com.project.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.accounts.constants.AccountsConstants;
import com.project.accounts.dto.CustomerDto;
import com.project.accounts.dto.ErrorResponseDto;
import com.project.accounts.dto.ResponseDto;
import com.project.accounts.service.IAccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Tag(name = "CRUD REST APIs for Accounts in BankApp", description = "CRUD REST APIs for Accounts in BankApp for CREATE, UPDATE, FETCH and DELETE account details")
@RestController
@RequestMapping("/api/accounts")
@Validated
public class AccountsController {

    @Autowired
    private IAccountsService accountsService;

    @Operation(summary = "CREATE API", description = "REST API for creating account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTS Status Created"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(summary = "FETCH API", description = "REST API for fetching customer and account details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTS Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(
            @RequestParam @Pattern(regexp = "$|[0-9]{10}", message = "MobileNumber must be 10 digits") String mobileNumber) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(accountsService.fetchAccount(mobileNumber));
    }

    @Operation(summary = "UPDATE API", description = "REST API for updating customer and account details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTS Status OK"),
            @ApiResponse(responseCode = "417", description = "HTTP STATUS Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if (isUpdated)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
    }

    @Operation(summary = "DELETE API", description = "REST API for deleting customer and account details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTS Status OK"),
            @ApiResponse(responseCode = "417", description = "HTTP STATUS Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS Internal Server Error")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(
            @RequestParam @Pattern(regexp = "$|[0-9]{10}", message = "MobileNumber must be 10 digits") String mobileNumber) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if (isDeleted)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
    }
}

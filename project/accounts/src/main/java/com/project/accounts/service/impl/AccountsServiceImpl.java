package com.project.accounts.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.accounts.constants.AccountsConstants;
import com.project.accounts.dto.AccountsDto;
import com.project.accounts.dto.CustomerDto;
import com.project.accounts.entity.Accounts;
import com.project.accounts.entity.Customer;
import com.project.accounts.exception.CustomerAlreadyExistsException;
import com.project.accounts.exception.ResourceNotFoundException;
import com.project.accounts.mapper.AccountsMapper;
import com.project.accounts.mapper.CustomerMapper;
import com.project.accounts.repository.AccountsRepository;
import com.project.accounts.repository.CustomerRepository;
import com.project.accounts.service.IAccountsService;

@Service
public class AccountsServiceImpl implements IAccountsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent())
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number " +
                    customerDto.getMobileNumber());
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long accountNumber = 100000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(accountNumber);
        newAccount.setAccountType(AccountsConstants.SAVING);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(account, new AccountsDto());
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts account = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber",
                            accountsDto.getAccountNumber().toString()));
            AccountsMapper.mapToAccounts(accountsDto, account);
            accountsRepository.save(account);
            Customer customer = customerRepository.findById(account.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", account.getCustomerId().toString()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}

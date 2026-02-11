package com.BankManagmentSystem.interfaces;

import com.BankManagmentSystem.dtos.AccountRequestDTO;
import com.BankManagmentSystem.dtos.AccountResponseDTO;

public interface CustomerService {

    AccountResponseDTO openAccount(AccountRequestDTO accountRequestDTO);

}

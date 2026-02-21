package com.BankManagmentSystem.interfaces;

import java.util.List;

import com.BankManagmentSystem.dtos.ManagerRegister;
import com.BankManagmentSystem.dtos.RegisterRequestDTO;
import com.BankManagmentSystem.dtos.RegistrationResponseDTO;
import com.BankManagmentSystem.exceptions.AdminAlreadyExists;
import com.BankManagmentSystem.exceptions.EmailAlreadyExists;
import com.BankManagmentSystem.exceptions.IncorrectPasswordException;
import com.BankManagmentSystem.exceptions.MobileNumberAlreadyExists;

public interface AuthService {

        RegistrationResponseDTO registerAdmin(RegisterRequestDTO dto)
                        throws AdminAlreadyExists, EmailAlreadyExists,
                        MobileNumberAlreadyExists, IncorrectPasswordException;

        RegistrationResponseDTO registerManager(ManagerRegister dto)
                        throws EmailAlreadyExists, MobileNumberAlreadyExists,
                        IncorrectPasswordException;

        RegistrationResponseDTO registerCustomer(RegisterRequestDTO dto)
                        throws EmailAlreadyExists, MobileNumberAlreadyExists,
                        IncorrectPasswordException;

        RegistrationResponseDTO getByEmail(String email);

        List<RegistrationResponseDTO> getAllUsers();
}

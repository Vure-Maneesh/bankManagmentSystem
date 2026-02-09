package com.BankManagmentSystem.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BankManagmentSystem.dtos.ManagerRegister;
import com.BankManagmentSystem.dtos.RegisterRequestDTO;
import com.BankManagmentSystem.dtos.UserResponseDTO;
import com.BankManagmentSystem.exceptions.AdminAlreadyExists;
import com.BankManagmentSystem.exceptions.EmailAlreadyExists;
import com.BankManagmentSystem.exceptions.IncorrectPasswordException;
import com.BankManagmentSystem.exceptions.MobileNumberAlreadyExists;
import com.BankManagmentSystem.interfaces.AuthService;
import com.BankManagmentSystem.model.Branch;
import com.BankManagmentSystem.model.KycStatus;
import com.BankManagmentSystem.model.Role;
import com.BankManagmentSystem.model.User;
import com.BankManagmentSystem.repository.BranchRepository;
import com.BankManagmentSystem.repository.UserRepository;

@Service
public class AuthServiceImple implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder encoder;

    // ---------- COMMON VALIDATION ----------
    private void validateEmailAndMobile(String email, Long mobile)
            throws EmailAlreadyExists, MobileNumberAlreadyExists {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExists("Email already registered");
        }
        if (userRepository.existsByMobile(mobile)) {
            throw new MobileNumberAlreadyExists("Mobile number already registered");
        }
    }

    private void validatePasswordMatch(String password, String confirmPassword) throws IncorrectPasswordException {
        if (!password.equals(confirmPassword)) {
            throw new IncorrectPasswordException("Password and Confirm Password do not match");
        }
    }

    // ---------- CUSTOMER ----------
    @Override
    public UserResponseDTO registerCustomer(RegisterRequestDTO dto)
            throws EmailAlreadyExists, MobileNumberAlreadyExists, IncorrectPasswordException {

        // ✅ 1. confirm password check
        validatePasswordMatch(dto.getPassword(), dto.getConfirmPassword());

        // ✅ 2. uniqueness check
        validateEmailAndMobile(dto.getEmail(), dto.getMobile());

        User user = new User();
        user.setName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setStatus(KycStatus.APPROVED);

        userRepository.save(user);
        return mapper.map(user, UserResponseDTO.class);
    }

    // ---------- ADMIN ----------
    @Override
    public UserResponseDTO registerAdmin(RegisterRequestDTO dto)
            throws AdminAlreadyExists, EmailAlreadyExists, MobileNumberAlreadyExists, IncorrectPasswordException {

        // ✅ confirm password
        validatePasswordMatch(dto.getPassword(), dto.getConfirmPassword());

        if (userRepository.existsByRole(Role.ADMIN)) {
            throw new AdminAlreadyExists("Admin already exists");
        }

        validateEmailAndMobile(dto.getEmail(), dto.getMobile());

        User admin = new User();
        admin.setName(dto.getFullName());
        admin.setEmail(dto.getEmail());
        admin.setMobile(dto.getMobile());
        admin.setPassword(encoder.encode(dto.getPassword()));
        admin.setRole(Role.ADMIN);
        admin.setStatus(KycStatus.APPROVED);

        userRepository.save(admin);
        return mapper.map(admin, UserResponseDTO.class);
    }

    // ---------- MANAGER ----------
    @Override
    public UserResponseDTO registerManager(ManagerRegister dto)
            throws EmailAlreadyExists, MobileNumberAlreadyExists, IncorrectPasswordException {

        // ✅ confirm password
        validatePasswordMatch(dto.getPassword(), dto.getConfirmPassword());

        validateEmailAndMobile(dto.getEmail(), dto.getMobile());

        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        User manager = new User();
        manager.setName(dto.getFullName());
        manager.setEmail(dto.getEmail());
        manager.setMobile(dto.getMobile());
        manager.setPassword(encoder.encode(dto.getPassword()));
        manager.setRole(Role.BRANCH_MANAGER);
        manager.setStatus(KycStatus.PENDING);
        manager.setBranch(branch);

        userRepository.save(manager);
        return mapper.map(manager, UserResponseDTO.class);
    }

    // ---------- FETCH USER ----------
    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

package com.BankManagmentSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BankManagmentSystem.dtos.ManagerRegister;
import com.BankManagmentSystem.dtos.RegisterRequestDTO;
import com.BankManagmentSystem.dtos.RegistrationResponseDTO;
import com.BankManagmentSystem.exceptions.AdminAlreadyExists;
import com.BankManagmentSystem.exceptions.EmailAlreadyExists;
import com.BankManagmentSystem.exceptions.IncorrectPasswordException;
import com.BankManagmentSystem.exceptions.MobileNumberAlreadyExists;
import com.BankManagmentSystem.interfaces.AuthService;
import com.BankManagmentSystem.interfaces.EmailService;
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
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    // ================= VALIDATIONS =================

    private void validateEmailAndMobile(String email, String mobile)
            throws EmailAlreadyExists, MobileNumberAlreadyExists {

        if (userRepository.existsByEmail(email))
            throw new EmailAlreadyExists("Email already registered");

        if (userRepository.existsByMobile(mobile))
            throw new MobileNumberAlreadyExists("Mobile already registered");
    }

    private void validatePasswordMatch(String password, String confirmPassword)
            throws IncorrectPasswordException {

        if (!password.equals(confirmPassword))
            throw new IncorrectPasswordException("Passwords do not match");
    }

    // ================= CUSTOMER =================

    @Override
    public RegistrationResponseDTO registerCustomer(RegisterRequestDTO dto)
            throws EmailAlreadyExists, MobileNumberAlreadyExists,
            IncorrectPasswordException {

        validatePasswordMatch(dto.getPassword(), dto.getConfirmPassword());
        validateEmailAndMobile(dto.getEmail(), dto.getMobile());

        User user = new User();
        user.setName(dto.getName());
        user.setDob(dto.getDob());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setStatus(KycStatus.APPROVED);

        userRepository.save(user);

        return mapToDTO(user, "Customer registered successfully");
    }

    // ================= ADMIN =================

    @Override
    public RegistrationResponseDTO registerAdmin(RegisterRequestDTO dto)
            throws AdminAlreadyExists, EmailAlreadyExists,
            MobileNumberAlreadyExists, IncorrectPasswordException {

        validatePasswordMatch(dto.getPassword(), dto.getConfirmPassword());

        if (userRepository.existsByRole(Role.ADMIN))
            throw new AdminAlreadyExists("Admin already exists");

        validateEmailAndMobile(dto.getEmail(), dto.getMobile());

        User admin = new User();
        admin.setName(dto.getName());
        admin.setDob(dto.getDob());
        admin.setEmail(dto.getEmail());
        admin.setMobile(dto.getMobile());
        admin.setPassword(encoder.encode(dto.getPassword()));
        admin.setRole(Role.ADMIN);
        admin.setStatus(KycStatus.APPROVED);

        userRepository.save(admin);

        return mapToDTO(admin, "Admin registered successfully");
    }

    // ================= MANAGER =================

    @Override
    public RegistrationResponseDTO registerManager(ManagerRegister dto)
            throws EmailAlreadyExists, MobileNumberAlreadyExists,
            IncorrectPasswordException {

        validatePasswordMatch(dto.getPassword(), dto.getConfirmPassword());
        validateEmailAndMobile(dto.getEmail(), dto.getMobile());

        Branch branch = branchRepository.findById((long) dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        // Check if branch already has manager
        if (userRepository.existsByBranchAndRole(branch, Role.BRANCH_MANAGER)) {
            throw new RuntimeException("Branch already has a manager");
        }

        User manager = new User();
        manager.setName(dto.getName());
        manager.setDob(dto.getDob());
        manager.setEmail(dto.getEmail());
        manager.setMobile(dto.getMobile());
        manager.setPassword(encoder.encode(dto.getPassword()));
        manager.setRole(Role.BRANCH_MANAGER);
        manager.setStatus(KycStatus.PENDING);
        manager.setBranch(branch);

        userRepository.save(manager);

        emailService.sendPendingApprovalEmail(manager);

        return mapToDTO(manager,
                "Manager registered successfully. Awaiting admin approval");
    }

    // ================= FETCH USER =================

    @Override
    public RegistrationResponseDTO getByEmail(String email) {

        User user = userRepository.findByEmailWithDetails(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDTO(user, null);
    }

    @Override
    public List<RegistrationResponseDTO> getAllUsers() {

        return userRepository.findAllWithBranchAndBank()
                .stream()
                .map(user -> mapToDTO(user, null))
                .toList();
    }

    // ================= DTO MAPPING =================

    private RegistrationResponseDTO mapToDTO(User user, String message) {

        RegistrationResponseDTO dto = new RegistrationResponseDTO();

        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMobile(user.getMobile());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setMessage(message);
        dto.setToken(null);

        if (user.getBranch() != null) {
            dto.setBranchId(user.getBranch().getBranchId());
            dto.setBranchName(user.getBranch().getName());

            if (user.getBranch().getBank() != null) {
                dto.setBankId(user.getBranch().getBank().getBankId());
                dto.setBankName(user.getBranch().getBank().getName());
            }
        }

        return dto;
    }
}

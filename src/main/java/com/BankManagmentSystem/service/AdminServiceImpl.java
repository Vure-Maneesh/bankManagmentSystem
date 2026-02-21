package com.BankManagmentSystem.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BankManagmentSystem.dtos.*;
import com.BankManagmentSystem.dtos.BankRequestDTO.*;
import com.BankManagmentSystem.dtos.BranchRequestDTO.*;
import com.BankManagmentSystem.exceptions.*;
import com.BankManagmentSystem.interfaces.AdminService;
import com.BankManagmentSystem.interfaces.EmailService;
import com.BankManagmentSystem.model.*;
import com.BankManagmentSystem.repository.*;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    // ================= CREATE BANK =================

    @Override
    public BankResponseDTO createBank(BankRequestDTO dto)
            throws BankAlreadyExits {

        if (bankRepository.count() >= 1) {
            throw new BankAlreadyExits("Bank already exists");
        }

        Bank bank = new Bank();
        bank.setName(dto.getBankName());
        bank.setHeadOffice(dto.getHeadOffice());
        bank.setIfscCode(dto.getIfscCode());

        bankRepository.save(bank);

        return mapper.map(bank, BankResponseDTO.class);
    }

    // ================= GET BANK =================

    @Override
    public BankResponseDTO getBank() {

        Bank bank = bankRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new BankNotFoundException("No bank configured yet"));

        return mapper.map(bank, BankResponseDTO.class);
    }

    // ================= CREATE BRANCH =================

    @Override
    public BranchResponseDTO createBranch(BranchRequestDTO dto)
            throws BankNotFoundException, BranchAlreadyExists {

        Bank bank = bankRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new BankNotFoundException("Bank not created yet"));

        if (branchRepository.existsByNameAndBank(dto.getName(), bank)) {
            throw new BranchAlreadyExists("Branch already exists");
        }

        Branch branch = new Branch();
        branch.setName(dto.getName());
        branch.setCity(dto.getCity());
        branch.setAddress(dto.getAddress());
        branch.setBank(bank);

        branchRepository.save(branch);

        return mapper.map(branch, BranchResponseDTO.class);
    }

    // ================= GET ALL BRANCHES =================

    @Override
    public List<BranchResponseDTO> getAllBranches()
            throws BankNotFoundException {

        Bank bank = bankRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new BankNotFoundException("Bank not found"));

        return branchRepository.findByBank(bank)
                .stream()
                .map(branch -> mapper.map(branch, BranchResponseDTO.class))
                .toList();
    }

    // ================= APPROVE MANAGER =================

    @Override
    public ManagerApprovalResponseDTO approveManager(Long managerId)
            throws ManagerNotFound {

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new ManagerNotFound("Manager not found"));

        if (manager.getRole() != Role.BRANCH_MANAGER) {
            throw new IllegalStateException("User is not a manager");
        }

        manager.setStatus(KycStatus.APPROVED);
        userRepository.save(manager);

        emailService.sendApprovalEmail(manager);

        return new ManagerApprovalResponseDTO(
                manager.getUserId(),
                manager.getName(),
                manager.getStatus(),
                "Manager approved successfully");
    }

    // ================= TRANSFER MANAGER =================

    @Override
    public ManagerTransferResponseDTO transferManager(
            ManagerTransferRequestDTO requestDTO)
            throws ManagerNotFound, BranchNotFound {

        User manager = userRepository.findById(requestDTO.getManagerId())
                .orElseThrow(() -> new ManagerNotFound("Manager Not Found"));

        Branch targetBranch = branchRepository.findById(
                requestDTO.getTargetBranchId())
                .orElseThrow(() -> new BranchNotFound("Target branch not found"));

        manager.setBranch(targetBranch);

        branchRepository.save(targetBranch);
        userRepository.save(manager);

        return new ManagerTransferResponseDTO(
                manager.getUserId(),
                null,
                targetBranch.getBranchId(),
                "Manager transferred successfully");
    }

    // ================= GET ALL MANAGERS =================

    @Override
    public List<RegistrationResponseDTO> getAllManagers() {

        return userRepository.findAllWithBranchAndBank()
                .stream()
                .filter(user -> user.getRole() == Role.BRANCH_MANAGER)
                .map(this::mapToDTO)
                .toList();
    }

    // ================= ENTITY → DTO MAPPING =================

    private RegistrationResponseDTO mapToDTO(User user) {

        RegistrationResponseDTO dto = new RegistrationResponseDTO();

        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMobile(user.getMobile());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());

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

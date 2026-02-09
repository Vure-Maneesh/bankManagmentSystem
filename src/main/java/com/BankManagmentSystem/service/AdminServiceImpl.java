package com.BankManagmentSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BankManagmentSystem.dtos.BankRequestDTO.BankRequestDTO;
import com.BankManagmentSystem.dtos.BankRequestDTO.BankResponseDTO;
import com.BankManagmentSystem.dtos.BranchRequestDTO.BranchRequestDTO;
import com.BankManagmentSystem.dtos.BranchRequestDTO.BranchResponseDTO;
import com.BankManagmentSystem.exceptions.BankAlreadyExits;
import com.BankManagmentSystem.exceptions.BankNotFoundException;
import com.BankManagmentSystem.exceptions.BranchAlreadyExists;
import com.BankManagmentSystem.exceptions.BranchNotFound;
import com.BankManagmentSystem.exceptions.ManagerNotFound;
import com.BankManagmentSystem.interfaces.AdminService;
import com.BankManagmentSystem.model.Bank;
import com.BankManagmentSystem.model.Branch;
import com.BankManagmentSystem.model.KycStatus;
import com.BankManagmentSystem.model.Role;
import com.BankManagmentSystem.model.User;
import com.BankManagmentSystem.repository.BankRepository;
import com.BankManagmentSystem.repository.BranchRepository;
import com.BankManagmentSystem.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    // ---------- CREATE BANK (ONLY ONCE) ----------
    @Override
    public BankResponseDTO createBank(BankRequestDTO dto) throws BankAlreadyExits {

        if (bankRepository.count() >= 1) {
            throw new BankAlreadyExits("Bank already exists. Cannot create another bank");
        }

        Bank bank = new Bank();
        bank.setName(dto.getBankName());
        bank.setHeadOffice(dto.getHeadOffice());
        bank.setIfscCode(dto.getIfscCode());

        bankRepository.save(bank);
        return mapper.map(bank, BankResponseDTO.class);
    }

    // ---------- CREATE BRANCH ----------
    @Override
    public BranchResponseDTO createBranch(BranchRequestDTO dto)
            throws BankNotFoundException, BranchAlreadyExists {

        // get the single existing bank
        Bank bank = bankRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new BankNotFoundException("Bank not created yet"));

        // prevent duplicate branch names under same bank
        if (branchRepository.existsByNameAndBank(dto.getBranchName(), bank)) {
            throw new BranchAlreadyExists("Branch already exists under this bank");
        }

        Branch branch = new Branch();
        branch.setName(dto.getBranchName());
        branch.setCity(dto.getCity());
        branch.setAddress(dto.getAddress());
        branch.setBank(bank);

        branchRepository.save(branch);
        return mapper.map(branch, BranchResponseDTO.class);
    }

    // ---------- GET ALL BRANCHES OF BANK ----------
    @Override
    public List<BranchResponseDTO> getAllBranches() throws BankNotFoundException {

        Bank bank = bankRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new BankNotFoundException("Bank not found"));

        return branchRepository.findByBank(bank)
                .stream()
                .map(branch -> mapper.map(branch, BranchResponseDTO.class))
                .collect(Collectors.toList());
    }

    // ---------- APPROVE MANAGER ----------
    @Override
    public void approveManager(Long managerId) throws ManagerNotFound {

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new ManagerNotFound("Manager not found"));

        if (manager.getRole() != Role.BRANCH_MANAGER) {
            throw new RuntimeException("User is not a manager");
        }

        manager.setStatus(KycStatus.APPROVED);
        userRepository.save(manager);
    }

    @Override
    public void transferManager(Long managerId, Long targetBranchId) throws ManagerNotFound, BranchNotFound {
        User manager = userRepository.findById(managerId).orElseThrow(() -> new ManagerNotFound("Manager Not Found"));

        if (manager.getRole() != Role.BRANCH_MANAGER) {
            throw new RuntimeException("User is not Branch Manager");
        }

        if (manager.getStatus() != KycStatus.APPROVED) {
            throw new RuntimeException("Approved manager can be Transfer");
        }

        Branch targetbranch = branchRepository.findById(targetBranchId)
                .orElseThrow(() -> new BranchNotFound("Branch not Exists"));

        if (targetbranch.getBranchManager() != null) {
            throw new RuntimeException("Branch Has Already Assigned With Manager ");
        }

        Branch oldBranch = manager.getBranch();

        if (oldBranch != null) {
            oldBranch.setBranchManager(null);
            branchRepository.save(oldBranch);

        }

        targetbranch.setBranchManager(manager);
        manager.setBranch(targetbranch);

        branchRepository.save(targetbranch);
        userRepository.save(manager);
    }

}

package com.BankManagmentSystem.interfaces;

import java.util.List;

import com.BankManagmentSystem.dtos.BankRequestDTO.BankRequestDTO;
import com.BankManagmentSystem.dtos.BankRequestDTO.BankResponseDTO;
import com.BankManagmentSystem.dtos.BranchRequestDTO.BranchRequestDTO;
import com.BankManagmentSystem.dtos.BranchRequestDTO.BranchResponseDTO;
import com.BankManagmentSystem.exceptions.BankAlreadyExits;
import com.BankManagmentSystem.exceptions.BankNotFoundException;
import com.BankManagmentSystem.exceptions.BranchAlreadyExists;
import com.BankManagmentSystem.exceptions.BranchNotFound;
import com.BankManagmentSystem.exceptions.ManagerNotFound;

public interface AdminService {

    BankResponseDTO createBank(BankRequestDTO dto) throws BankAlreadyExits;

    BranchResponseDTO createBranch(BranchRequestDTO dto) throws BankNotFoundException, BranchAlreadyExists;

    void approveManager(Long managerId) throws ManagerNotFound;

    List<BranchResponseDTO> getAllBranches() throws BankNotFoundException;

    void transferManager(Long managerId, Long targetBranchId)
            throws ManagerNotFound, BranchNotFound;

}

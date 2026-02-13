package com.BankManagmentSystem.interfaces;

import java.util.List;

import com.BankManagmentSystem.dtos.ManagerApprovalResponseDTO;
import com.BankManagmentSystem.dtos.ManagerTransferRequestDTO;
import com.BankManagmentSystem.dtos.ManagerTransferResponseDTO;
import com.BankManagmentSystem.dtos.BankRequestDTO.BankRequestDTO;
import com.BankManagmentSystem.dtos.BankRequestDTO.BankResponseDTO;
import com.BankManagmentSystem.dtos.BranchRequestDTO.BranchRequestDTO;
import com.BankManagmentSystem.dtos.BranchRequestDTO.BranchResponseDTO;
import com.BankManagmentSystem.exceptions.BankAlreadyExits;
import com.BankManagmentSystem.exceptions.BankNotFoundException;
import com.BankManagmentSystem.exceptions.BranchAlreadyExists;
import com.BankManagmentSystem.exceptions.BranchNotFound;
import com.BankManagmentSystem.exceptions.ManagerNotFound;
import com.BankManagmentSystem.model.User;

public interface AdminService {

        BankResponseDTO createBank(BankRequestDTO dto) throws BankAlreadyExits;

        BranchResponseDTO createBranch(BranchRequestDTO dto) throws BankNotFoundException, BranchAlreadyExists;

        public ManagerApprovalResponseDTO approveManager(Long managerId)
                        throws ManagerNotFound;

        List<BranchResponseDTO> getAllBranches() throws BankNotFoundException;

        ManagerTransferResponseDTO transferManager(ManagerTransferRequestDTO request)
                        throws ManagerNotFound, BranchNotFound;

        List<User> getAllManagers();

}

package com.BankManagmentSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.BankManagmentSystem.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

}

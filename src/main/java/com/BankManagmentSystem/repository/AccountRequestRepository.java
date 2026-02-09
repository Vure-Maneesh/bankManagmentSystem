package com.BankManagmentSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BankManagmentSystem.model.AccountRequest;
import com.BankManagmentSystem.model.KycStatus;

public interface AccountRequestRepository extends JpaRepository<AccountRequest, Long> {

    List<AccountRequest> findByStatus(KycStatus status);

}

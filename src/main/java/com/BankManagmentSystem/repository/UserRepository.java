package com.BankManagmentSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BankManagmentSystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

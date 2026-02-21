package com.BankManagmentSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BankManagmentSystem.model.Branch;
import com.BankManagmentSystem.model.KycStatus;
import com.BankManagmentSystem.model.Role;
import com.BankManagmentSystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // ================= BASIC METHODS =================

    Optional<User> findByEmail(String email);

    Optional<User> findByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    boolean existsByRole(Role role);

    List<User> findByStatus(KycStatus status);

    List<User> findByRole(Role role);

    // ================= FETCH JOIN METHODS =================

    // ✅ Fetch branch and bank for all users (avoids Hibernate proxy error)
    @Query("""
                SELECT u FROM User u
                LEFT JOIN FETCH u.branch b
                LEFT JOIN FETCH b.bank
            """)
    List<User> findAllWithBranchAndBank();

    // ✅ Fetch branch and bank for single user by email
    @Query("""
                SELECT u FROM User u
                LEFT JOIN FETCH u.branch b
                LEFT JOIN FETCH b.bank
                WHERE u.email = :email
            """)
    Optional<User> findByEmailWithDetails(@Param("email") String email);

    boolean existsByBranchAndRole(Branch branch, Role role);
}

package com.BankManagmentSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.BankManagmentSystem.configurations.JwtService;
import com.BankManagmentSystem.dtos.*;
import com.BankManagmentSystem.exceptions.*;
import com.BankManagmentSystem.interfaces.AuthService;
import com.BankManagmentSystem.model.User;
import com.BankManagmentSystem.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;
        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;
        private final UserRepository userRepository;

        // =========================
        // ADMIN REGISTRATION
        // =========================
        @PostMapping("/admin/register")
        public ResponseEntity<RegistrationResponseDTO> adminRegister(
                        @RequestBody @Valid RegisterRequestDTO requestDTO)
                        throws AdminAlreadyExists, EmailAlreadyExists,
                        MobileNumberAlreadyExists, IncorrectPasswordException {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(authService.registerAdmin(requestDTO));
        }

        // =========================
        // MANAGER REGISTRATION
        // =========================
        @PostMapping("/manager/register")
        public ResponseEntity<RegistrationResponseDTO> managerRegister(
                        @RequestBody @Valid ManagerRegister managerRegister)
                        throws EmailAlreadyExists, MobileNumberAlreadyExists,
                        IncorrectPasswordException {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(authService.registerManager(managerRegister));
        }

        // =========================
        // CUSTOMER REGISTRATION
        // =========================
        @PostMapping("/customer/register")
        public ResponseEntity<RegistrationResponseDTO> customerRegister(
                        @RequestBody @Valid RegisterRequestDTO dto)
                        throws EmailAlreadyExists, MobileNumberAlreadyExists,
                        IncorrectPasswordException {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(authService.registerCustomer(dto));
        }

        // =========================
        // LOGIN (JWT)
        // =========================
        @PostMapping("/login")
        public ResponseEntity<LoginResponseDTO> login(
                        @RequestBody @Valid LoginRequest request) {

                // 1. Authenticate credentials
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                // 2. Fetch user
                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                // 3. Generate JWT
                String token = jwtService.generateToken(user);

                // 4. Optional branchId (manager / customer)
                Long branchId = user.getBranch() != null
                                ? user.getBranch().getBranchId()
                                : null;

                return ResponseEntity.ok(
                                new LoginResponseDTO(
                                                token,
                                                user.getUserId(),
                                                user.getRole(),
                                                user.getName(),
                                                branchId));
        }

        // =========================
        // LOGOUT (STATELESS JWT)
        // =========================
        @PostMapping("/logout")
        public ResponseEntity<String> logout() {
                // JWT is stateless → frontend deletes token
                return ResponseEntity.ok(
                                "Logout successful. Please remove token from client.");
        }
}

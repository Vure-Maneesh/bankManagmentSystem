// package com.BankManagmentSystem.controller;

// import java.security.Principal;
// import java.util.List;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.BankManagmentSystem.dtos.BeneficiaryRequestDTO;
// import com.BankManagmentSystem.dtos.BeneficiaryResponseDTO;

// @RestController
// @RequestMapping("/api/beneficiaries")
// public class BeneficiaryController {

// @PostMapping
// public ResponseEntity<BeneficiaryResponseDTO> add(
// @RequestBody BeneficiaryRequestDTO request,
// Principal principal) {

// return ResponseEntity.ok(
// beneficiaryService.addBeneficiary(request, principal.getName()));
// }

// @GetMapping
// public ResponseEntity<List<BeneficiaryResponseDTO>> getAll(
// Principal principal) {

// return ResponseEntity.ok(
// beneficiaryService.getBeneficiaries(principal.getName()));
// }

// @DeleteMapping("/{id}")
// public ResponseEntity<String> delete(@PathVariable Long id) {
// beneficiaryService.deleteBeneficiary(id);
// return ResponseEntity.ok("Deleted successfully");
// }
// }

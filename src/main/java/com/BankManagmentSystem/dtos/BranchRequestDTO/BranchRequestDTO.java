package com.BankManagmentSystem.dtos.BranchRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchRequestDTO {

    private String name;
    private String city;
    private String address;
    private Long bankId;

}

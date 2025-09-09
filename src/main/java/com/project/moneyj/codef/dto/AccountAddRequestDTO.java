package com.project.moneyj.codef.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountAddRequestDTO {

    private String connectedId; // 서버에서 userId로 조회해서 넣어도 됨
    private List<AccountCreateRequestDTO.AccountInput> accountList;
}

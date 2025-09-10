package com.project.moneyj.codef.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountListReqDTO {

    private String countryCode;   // "KR"
    private String businessType;  // "BK"
    private String clientType;    // "P"
    private String organization;  // "0004" 등
    private String connectedId;   // CID
}

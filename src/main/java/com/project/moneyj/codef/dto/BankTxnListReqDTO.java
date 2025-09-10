package com.project.moneyj.codef.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankTxnListReqDTO {

    private String organization;   // "0004"
    private String account;        // 계좌번호
    private String startDate;      // YYYYMMDD
    private String endDate;        // YYYYMMDD

    // CODEF는 "0"/"1" 사용. 클라이언트가 "desc"/"asc"를 보내면 아래에서 보정
    private String orderBy;        // "0"(asc) | "1"(desc)

    // CODEF는 inquiryType 사용. 기본 "1"
    private String inquiryType;    // null이면 "1"로 보정

    private Integer pageNo;        // 옵션
    private Integer limit;         // 옵션

    // 호환용: 예전 클라가 보낸 inOutType를 받아두기만 함(전송 X)
    @JsonAlias("inOutType")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String legacyInOutType;
}

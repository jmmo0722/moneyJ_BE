package com.project.moneyj.codef.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountListResponseDTO {

    private Map<String, Object> result;
    private List<Map<String, Object>> data; // 기관별 등록 상태 등
}

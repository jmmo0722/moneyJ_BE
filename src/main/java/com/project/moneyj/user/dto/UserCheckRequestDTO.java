package com.project.moneyj.user.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCheckRequestDTO {
    private List<String> emails;
}

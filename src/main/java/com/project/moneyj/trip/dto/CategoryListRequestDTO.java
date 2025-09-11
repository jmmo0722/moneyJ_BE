package com.project.moneyj.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CategoryListRequestDTO {

    @NotNull
    private List<CategoryDTO> categoryDTOList;
}

package com.project.moneyj.trip.dto;

import com.project.moneyj.trip.domain.Category;
import com.project.moneyj.trip.domain.TripMember;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryDTO {

    @NotNull
    private String categoryName;

    @NotNull @PositiveOrZero
    private Integer amount;

    public static CategoryDTO fromEntity(Category category){
        return CategoryDTO.builder()
                .categoryName(category.getCategoryName())
                .amount(category.getAmount())
                .build();
    }
}

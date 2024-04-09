package com.tacm.tabooksapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {
    private Long bookId;

    private Integer quantity;

    private Double price;
}

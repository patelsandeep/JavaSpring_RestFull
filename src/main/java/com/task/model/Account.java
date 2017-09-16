package com.task.model;

import lombok.Data;

import java.math.BigDecimal;
import lombok.Builder;

@Data
@Builder
public class Account {

    private Integer account_id;
    private String customername;
    private String currency;
    private BigDecimal amount;
}

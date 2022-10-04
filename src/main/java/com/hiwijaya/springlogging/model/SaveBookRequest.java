package com.hiwijaya.springlogging.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaveBookRequest {

    private Integer id;
    private String title;
    private String author;
    private BigDecimal price = BigDecimal.ZERO;

}

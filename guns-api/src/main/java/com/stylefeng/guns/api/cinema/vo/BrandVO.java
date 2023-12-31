package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.BufferedOutputStream;
import java.io.Serializable;
@Data
public class BrandVO implements Serializable {
    private String brandId;

    private String brandName;

    private Boolean isActive;
}

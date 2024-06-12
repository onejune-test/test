package com.example.demo.constant;

/**
 * 描述
 *
 * @author 王俊
 * @date create in 2023/8/31
 */
public enum PriceUnit {
    /**
     *
     */
    K(1, "个"), G(2, "只");

    private final Integer code;
    private final String name;

    PriceUnit(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

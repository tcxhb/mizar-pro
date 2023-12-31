package com.tcxhb.mizar.core.constants.enums;


/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/9/21
 */
public enum UserTypeEnum {

    SUPPER(1, "管理员"),
    NORMAL(2, "普通");

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    private Integer type;
    private String name;

    UserTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
    public boolean eq(Integer type){
        return this.type.equals(type);
    }
}

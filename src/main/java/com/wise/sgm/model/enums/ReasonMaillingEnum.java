package com.wise.sgm.model.enums;

public enum ReasonMaillingEnum {

    SUCESSO("SUCESSO"),
    SEM_SUCESSO("SEM SUCESSO");

    private String description;

    private ReasonMaillingEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}

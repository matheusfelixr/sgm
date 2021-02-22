package com.wise.sgm.model.enums;

public enum ReasonMailingEnum {

    SUCESSO("SUCESSO"),
    SEM_SUCESSO("SEM SUCESSO");

    private String description;

    private ReasonMailingEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}

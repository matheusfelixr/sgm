package com.matheusfelixr.sgm.model.enums;

public enum ImportStatusEnum {

    SUCCESS("Sucesso"),
    FAIL("Falha");

    private String description;

    private ImportStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}

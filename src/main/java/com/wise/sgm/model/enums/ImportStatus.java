package com.wise.sgm.model.enums;

public enum ImportStatus {

    SUCCESS("Sucesso"),
    FAIL("Falha");

    private String description;

    private ImportStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}

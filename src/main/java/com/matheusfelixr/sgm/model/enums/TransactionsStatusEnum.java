package com.matheusfelixr.sgm.model.enums;

public enum TransactionsStatusEnum {

    SUCCESS("Sucesso"),
    FAIL("Falha");

    private String description;

    private TransactionsStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}

package com.matheusfelixr.sgm.model.enums;

public enum TypeExportedEnum {

    ALREADY_EXPORTED("Já exportados "),
    NOT_EXPORTED("Não exportados"),
    BOTH("Ambos");

    private String description;

    private TypeExportedEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}

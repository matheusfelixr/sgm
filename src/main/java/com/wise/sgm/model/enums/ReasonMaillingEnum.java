package com.wise.sgm.model.enums;

import net.bytebuddy.implementation.bytecode.Throw;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;

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

    public static ReasonMaillingEnum findByDescription(String description) throws ValidationException {
        List<ReasonMaillingEnum> reasonMaillingEnums = Arrays.asList(ReasonMaillingEnum.values());
        for(ReasonMaillingEnum reasonMaillingEnum : reasonMaillingEnums){
            if(reasonMaillingEnum.getDescription().equals(description)){
                return reasonMaillingEnum;
            }
        }
        throw new ValidationException("Não foi encontrado tipo de status com essa descrição");
    }

}

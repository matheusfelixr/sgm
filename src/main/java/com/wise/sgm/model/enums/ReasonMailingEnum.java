package com.wise.sgm.model.enums;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;

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

    public static ReasonMailingEnum findByDescription(String description) throws ValidationException {
        List<ReasonMailingEnum> reasonMailingEnums = Arrays.asList(ReasonMailingEnum.values());
        for(ReasonMailingEnum reasonMailingEnum : reasonMailingEnums){
            if(reasonMailingEnum.getDescription().equals(description)){
                return reasonMailingEnum;
            }
        }
        throw new ValidationException("Não foi encontrado tipo de status com essa descrição");
    }

}

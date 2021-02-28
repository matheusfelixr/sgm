package com.wise.sgm.model.dto.mailingStatus;

import com.wise.sgm.model.enums.ReasonMailingEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReturnReansonMailingDTO {

    private String reasonMailing;

    public ReturnReansonMailingDTO(String reasonMailing) {
        this.reasonMailing = reasonMailing;
    }

    public static List<ReturnReansonMailingDTO> convertToListDTO(List<ReasonMailingEnum> entitys) {
        List<ReturnReansonMailingDTO> ret = new ArrayList<ReturnReansonMailingDTO>();
        for (ReasonMailingEnum entity : entitys) {
            ret.add(new ReturnReansonMailingDTO(entity.getDescription()));
        }
        return ret;
    }
}

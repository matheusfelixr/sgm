package com.wise.sgm.model.dto.maillingStatus;

import com.wise.sgm.model.domain.MaillingStatus;
import com.wise.sgm.model.enums.ReasonMaillingEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReturnReansonMaillingDTO {

    private String reasonMailling;

    public ReturnReansonMaillingDTO(String reasonMailling) {
        this.reasonMailling = reasonMailling;
    }

    public static List<ReturnReansonMaillingDTO> convertToListDTO(List<ReasonMaillingEnum> entitys) {
        List<ReturnReansonMaillingDTO> ret = new ArrayList<ReturnReansonMaillingDTO>();
        for (ReasonMaillingEnum entity : entitys) {
            ret.add(new ReturnReansonMaillingDTO(entity.getDescription()));
        }
        return ret;
    }
}

package com.wise.sgm.model.dto.maillingStatus;

import com.wise.sgm.model.domain.MaillingStatus;
import com.wise.sgm.model.enums.ReasonMaillingEnum;
import lombok.Data;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateMaillingStatusDTO {

    private String description;

    private String reasonMailling;


    public static MaillingStatus convertToEntity(CreateMaillingStatusDTO dto) throws ValidationException {
        MaillingStatus ret = new MaillingStatus();
        ret.setDescription(dto.getDescription());
        ret.setReasonMailling(ReasonMaillingEnum.findByDescription(dto.getReasonMailling()));
        return ret;
    }

    public static CreateMaillingStatusDTO convertToDTO(MaillingStatus entity) {
        CreateMaillingStatusDTO ret = new CreateMaillingStatusDTO();
        ret.setDescription(entity.getDescription());
        ret.setReasonMailling(entity.getReasonMailling().getDescription());
        return ret;
    }

    public static List<CreateMaillingStatusDTO> convertToListDTO(List<MaillingStatus> entitys) {
        List<CreateMaillingStatusDTO> ret = new ArrayList<CreateMaillingStatusDTO>();
        for (MaillingStatus entity : entitys) {
            ret.add(CreateMaillingStatusDTO.convertToDTO(entity));
        }
        return ret;
    }

    public static List<MaillingStatus> convertToListEntity(List<CreateMaillingStatusDTO> DTOs) throws ValidationException {
        List<MaillingStatus> ret = new ArrayList<MaillingStatus>();
        for (CreateMaillingStatusDTO dto : DTOs) {
            ret.add(CreateMaillingStatusDTO.convertToEntity(dto));
        }
        return ret;
    }
}

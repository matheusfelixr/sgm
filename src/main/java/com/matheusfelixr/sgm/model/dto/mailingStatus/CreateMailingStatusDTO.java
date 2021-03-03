package com.matheusfelixr.sgm.model.dto.mailingStatus;

import com.matheusfelixr.sgm.model.enums.ReasonMailingEnum;
import com.matheusfelixr.sgm.model.domain.MailingStatus;
import lombok.Data;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateMailingStatusDTO {

    private String description;

    private String reasonMailing;


    public static MailingStatus convertToEntity(CreateMailingStatusDTO dto) throws ValidationException {
        MailingStatus ret = new MailingStatus();
        ret.setDescription(dto.getDescription());
        ret.setReasonMailing(ReasonMailingEnum.findByDescription(dto.getReasonMailing()));
        return ret;
    }

    public static CreateMailingStatusDTO convertToDTO(MailingStatus entity) {
        CreateMailingStatusDTO ret = new CreateMailingStatusDTO();
        ret.setDescription(entity.getDescription());
        ret.setReasonMailing(entity.getReasonMailing().getDescription());
        return ret;
    }

    public static List<CreateMailingStatusDTO> convertToListDTO(List<MailingStatus> entitys) {
        List<CreateMailingStatusDTO> ret = new ArrayList<CreateMailingStatusDTO>();
        for (MailingStatus entity : entitys) {
            ret.add(CreateMailingStatusDTO.convertToDTO(entity));
        }
        return ret;
    }

    public static List<MailingStatus> convertToListEntity(List<CreateMailingStatusDTO> DTOs) throws ValidationException {
        List<MailingStatus> ret = new ArrayList<MailingStatus>();
        for (CreateMailingStatusDTO dto : DTOs) {
            ret.add(CreateMailingStatusDTO.convertToEntity(dto));
        }
        return ret;
    }
}

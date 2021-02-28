package com.wise.sgm.model.dto.mailingStatus;

import com.wise.sgm.model.domain.MailingStatus;
import com.wise.sgm.model.dto.dataControl.DataControlDTO;
import com.wise.sgm.model.enums.ReasonMailingEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MailingStatusDTO {

    private Long id;

    private String description;

    private ReasonMailingEnum reasonMailing;

    private DataControlDTO dataControl;

    public static MailingStatus convertToEntity(MailingStatusDTO dto) {
        MailingStatus ret = new MailingStatus();
        ret.setId(dto.getId());
        ret.setDescription(dto.getDescription());
        ret.setReasonMailing(dto.getReasonMailing());
        ret.setDataControl(DataControlDTO.convertToEntity(dto.getDataControl()));
        return ret;
    }

    public static MailingStatusDTO convertToDTO(MailingStatus entity) {
        MailingStatusDTO ret = new MailingStatusDTO();
        ret.setId(entity.getId());
        ret.setDescription(entity.getDescription());
        ret.setReasonMailing(entity.getReasonMailing());
        ret.setDataControl(DataControlDTO.convertToDTO(entity.getDataControl()));
        return ret;
    }

    public static List<MailingStatusDTO> convertToListDTO(List<MailingStatus> entitys) {
        List<MailingStatusDTO> ret = new ArrayList<MailingStatusDTO>();
        for (MailingStatus entity : entitys) {
            ret.add(MailingStatusDTO.convertToDTO(entity));
        }
        return ret;
    }

    public static List<MailingStatus> convertToListEntity(List<MailingStatusDTO> DTOs) {
        List<MailingStatus> ret = new ArrayList<MailingStatus>();
        for (MailingStatusDTO dto : DTOs) {
            ret.add(MailingStatusDTO.convertToEntity(dto));
        }
        return ret;
    }
}

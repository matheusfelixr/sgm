package com.wise.sgm.model.dto.maillingStatus;

import com.wise.sgm.model.domain.MaillingStatus;
import com.wise.sgm.model.dto.dataControl.DataControlDTO;
import com.wise.sgm.model.enums.ReasonMaillingEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MaillingStatusDTO {

    private Long id;

    private String description;

    private ReasonMaillingEnum reasonMailling;

    private DataControlDTO dataControl;

    public static MaillingStatus convertToEntity(MaillingStatusDTO dto) {
        MaillingStatus ret = new MaillingStatus();
        ret.setId(dto.getId());
        ret.setDescription(dto.getDescription());
        ret.setReasonMailling(dto.getReasonMailling());
        ret.setDataControl(DataControlDTO.convertToEntity(dto.getDataControl()));
        return ret;
    }

    public static MaillingStatusDTO convertToDTO(MaillingStatus entity) {
        MaillingStatusDTO ret = new MaillingStatusDTO();
        ret.setId(entity.getId());
        ret.setDescription(entity.getDescription());
        ret.setReasonMailling(entity.getReasonMailling());
        ret.setDataControl(DataControlDTO.convertToDTO(entity.getDataControl()));
        return ret;
    }

    public static List<MaillingStatusDTO> convertToListDTO(List<MaillingStatus> entitys) {
        List<MaillingStatusDTO> ret = new ArrayList<MaillingStatusDTO>();
        for (MaillingStatus entity : entitys) {
            ret.add(MaillingStatusDTO.convertToDTO(entity));
        }
        return ret;
    }

    public static List<MaillingStatus> convertToListEntity(List<MaillingStatusDTO> DTOs) {
        List<MaillingStatus> ret = new ArrayList<MaillingStatus>();
        for (MaillingStatusDTO dto : DTOs) {
            ret.add(MaillingStatusDTO.convertToEntity(dto));
        }
        return ret;
    }
}

package com.wise.sgm.model.dto.mailingType;

import com.wise.sgm.model.domain.MailingType;
import com.wise.sgm.model.dto.dataControl.DataControlDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MailingTypeDTO {

    private Long id;

    private String layout;

    private DataControlDTO dataControl;


    public static MailingType convertToEntity(MailingTypeDTO dto) {
        MailingType ret = new MailingType();
        ret.setId(dto.getId());
        ret.setLayout(dto.getLayout());
        ret.setDataControl(DataControlDTO.convertToEntity(dto.getDataControl()));
        return ret;
    }

    public static MailingTypeDTO convertToDTO(MailingType entity) {
        MailingTypeDTO ret = new MailingTypeDTO();
        ret.setId(entity.getId());
        ret.setLayout(entity.getLayout());
        ret.setDataControl(DataControlDTO.convertToDTO(entity.getDataControl()));
        return ret;
    }

    public static List<MailingTypeDTO> convertToListDTO(List<MailingType> entitys) {
        List<MailingTypeDTO> ret = new ArrayList<MailingTypeDTO>();
        for (MailingType entity : entitys) {
            ret.add(MailingTypeDTO.convertToDTO(entity));
        }
        return ret;
    }

    public static List<MailingType> convertToListEntity(List<MailingTypeDTO> DTOs) {
        List<MailingType> ret = new ArrayList<MailingType>();
        for (MailingTypeDTO dto : DTOs) {
            ret.add(MailingTypeDTO.convertToEntity(dto));
        }
        return ret;
    }


}

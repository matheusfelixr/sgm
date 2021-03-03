package com.matheusfelixr.sgm.model.dto.mailingType;

import com.matheusfelixr.sgm.model.dto.dataControl.DataControlDTO;
import com.matheusfelixr.sgm.model.domain.MailingType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MailingTypeDTO {

    private Long id;

    private String layout;

    private int numberOfFields;

    private DataControlDTO dataControl;


    public static MailingType convertToEntity(MailingTypeDTO dto) {
        MailingType ret = new MailingType();
        ret.setId(dto.getId());
        ret.setLayout(dto.getLayout());
        ret.setNumberOfFields(dto.getNumberOfFields());
        ret.setDataControl(DataControlDTO.convertToEntity(dto.getDataControl()));

        return ret;
    }

    public static MailingTypeDTO convertToDTO(MailingType entity) {
        MailingTypeDTO ret = new MailingTypeDTO();
        ret.setId(entity.getId());
        ret.setLayout(entity.getLayout());
        ret.setNumberOfFields(entity.getNumberOfFields());
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

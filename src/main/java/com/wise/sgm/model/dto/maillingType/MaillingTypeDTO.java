package com.wise.sgm.model.dto.maillingType;

import com.wise.sgm.model.domain.MaillingType;
import com.wise.sgm.model.dto.dataControl.DataControlDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MaillingTypeDTO {

    private Long id;

    private String layout;

    private DataControlDTO dataControl;


    public static MaillingType convertToEntity(MaillingTypeDTO dto) {
        MaillingType ret = new MaillingType();
        ret.setId(dto.getId());
        ret.setLayout(dto.getLayout());
        ret.setDataControl(DataControlDTO.convertToEntity(dto.getDataControl()));
        return ret;
    }

    public static MaillingTypeDTO convertToDTO(MaillingType entity) {
        MaillingTypeDTO ret = new MaillingTypeDTO();
        ret.setId(entity.getId());
        ret.setLayout(entity.getLayout());
        ret.setDataControl(DataControlDTO.convertToDTO(entity.getDataControl()));
        return ret;
    }

    public static List<MaillingTypeDTO> convertToListDTO(List<MaillingType> entitys) {
        List<MaillingTypeDTO> ret = new ArrayList<MaillingTypeDTO>();
        for (MaillingType entity : entitys) {
            ret.add(MaillingTypeDTO.convertToDTO(entity));
        }
        return ret;
    }

    public static List<MaillingType> convertToListEntity(List<MaillingTypeDTO> DTOs) {
        List<MaillingType> ret = new ArrayList<MaillingType>();
        for (MaillingTypeDTO dto : DTOs) {
            ret.add(MaillingTypeDTO.convertToEntity(dto));
        }
        return ret;
    }


}

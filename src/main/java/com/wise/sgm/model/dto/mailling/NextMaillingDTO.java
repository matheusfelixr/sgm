package com.wise.sgm.model.dto.mailling;

import com.wise.sgm.model.domain.Mailling;
import lombok.Data;

@Data
public class NextMaillingDTO {

    private Long id;

    private String campaignCode;

    private String campaign;

    private String customerKey;

    private String cpfCnpj;

    private String name;

    private String phone1;

    private String phone2;


    public static NextMaillingDTO convertToDTO(Mailling entity) {
        NextMaillingDTO ret = new NextMaillingDTO();
        if(entity.getMaillingLayout1() != null){
            ret.setId(entity.getId());
            ret.setCampaignCode(entity.getMaillingLayout1().getCODCAMPANHA());
            ret.setCampaign(entity.getMaillingLayout1().getCAMPANHA());
            ret.setCustomerKey(entity.getMaillingLayout1().getCUSTOMER_KEY());
            ret.setCpfCnpj(entity.getMaillingLayout1().getCPF_CNPJ());
            ret.setName(entity.getMaillingLayout1().getNOME());
            ret.setPhone1(entity.getMaillingLayout1().getTELEFONE_CONTATO_1());
            ret.setPhone2(entity.getMaillingLayout1().getTELEFONE_CONTATO_2());
            return ret;
        }
        return null;
    }

}

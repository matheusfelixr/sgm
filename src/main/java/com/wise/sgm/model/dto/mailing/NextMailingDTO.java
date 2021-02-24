package com.wise.sgm.model.dto.mailing;

import com.wise.sgm.model.domain.Mailing;
import lombok.Data;

@Data
public class NextMailingDTO {

    private Long id;

    private String campaignCode;

    private String campaign;

    private String customerKey;

    private String cpfCnpj;

    private String name;

    private String phone1;

    private String phone2;


    public static NextMailingDTO convertToDTO(Mailing entity) {
        NextMailingDTO ret = new NextMailingDTO();
        if(entity.getMailingLayout1() != null){
            ret.setId(entity.getId());
            ret.setCampaignCode(entity.getMailingLayout1().getCODCAMPANHA());
            ret.setCampaign(entity.getMailingLayout1().getCAMPANHA());
            ret.setCustomerKey(entity.getMailingLayout1().getCUSTOMER_KEY());
            ret.setCpfCnpj(entity.getMailingLayout1().getCPF_CNPJ());
            ret.setName(entity.getMailingLayout1().getNOME());
            ret.setPhone1(entity.getMailingLayout1().getTELEFONE_CONTATO_1());
            ret.setPhone2(entity.getMailingLayout1().getTELEFONE_CONTATO_2());
            return ret;
        }
        return null;
    }

}

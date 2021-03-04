package com.matheusfelixr.sgm.model.dto.exportMailingFile;

import com.matheusfelixr.sgm.model.domain.ExportMailingFile;
import com.matheusfelixr.sgm.model.domain.MailingStatus;
import com.matheusfelixr.sgm.model.dto.mailingStatus.CreateMailingStatusDTO;
import com.matheusfelixr.sgm.model.enums.ReasonMailingEnum;
import com.matheusfelixr.sgm.model.enums.TypeExportedEnum;
import lombok.Data;

import javax.xml.bind.ValidationException;
import java.util.Date;

@Data
public class GenerateFileRequestDTO {

    private TypeExportedEnum typeExported;

    private Date startDateExport;

    private Date endDateExport;

    public static ExportMailingFile convertToEntity(GenerateFileRequestDTO dto){
        ExportMailingFile ret = new ExportMailingFile();
        ret.setTypeExported(dto.getTypeExported());
        ret.setStartDateExport(dto.getStartDateExport());
        ret.setEndDateExport(dto.getEndDateExport());
        return ret;
    }


}

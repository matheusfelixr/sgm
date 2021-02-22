package com.wise.sgm.model.dto.ImportMailingFile;

import com.wise.sgm.model.domain.ImportMailingFile;
import com.wise.sgm.model.dto.cancellation.CancellationDTO;
import com.wise.sgm.model.dto.dataControl.DataControlDTO;
import com.wise.sgm.model.enums.ImportStatusEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ImportMailingFileDTO {

    private Long id;

    private Date startDate;

    private Date endDate;

    private String nameFile;

    private ImportStatusEnum importStatusEnum;

    private String error;

    private CancellationDTO cancellation;

    private DataControlDTO dataControl;



    public static ImportMailingFile convertToEntity(ImportMailingFileDTO dto) {
        ImportMailingFile ret = new ImportMailingFile();
        ret.setId(dto.getId());
        ret.setStartDate(dto.getStartDate());
        ret.setNameFile(dto.getNameFile());
        ret.setImportStatusEnum(dto.getImportStatusEnum());
        ret.setError(dto.getError());
        ret.setCancellation(CancellationDTO.convertToEntity(dto.getCancellation()));
        ret.setDataControl(DataControlDTO.convertToEntity(dto.getDataControl()));
        return ret;
    }

    public static ImportMailingFileDTO convertToDTO(ImportMailingFile entity) {
        ImportMailingFileDTO ret = new ImportMailingFileDTO();
        ret.setId(entity.getId());
        ret.setStartDate(entity.getStartDate());
        ret.setNameFile(entity.getNameFile());
        ret.setImportStatusEnum(entity.getImportStatusEnum());
        ret.setError(entity.getError());
        ret.setCancellation(CancellationDTO.convertToDTO(entity.getCancellation()));
        ret.setDataControl(DataControlDTO.convertToDTO(entity.getDataControl()));
        return ret;
    }

    public static List<ImportMailingFileDTO> convertToListDTO(List<ImportMailingFile> entitys) {
        List<ImportMailingFileDTO> ret = new ArrayList<ImportMailingFileDTO>();
        for (ImportMailingFile entity : entitys) {
            ret.add(ImportMailingFileDTO.convertToDTO(entity));
        }
        return ret;
    }

    public static List<ImportMailingFile> convertToListEntity(List<ImportMailingFileDTO> DTOs) {
        List<ImportMailingFile> ret = new ArrayList<ImportMailingFile>();
        for (ImportMailingFileDTO dto : DTOs) {
            ret.add(ImportMailingFileDTO.convertToEntity(dto));
        }
        return ret;
    }


}

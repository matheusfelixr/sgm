package com.wise.sgm.model.dto.ImportMaillingFile;

import com.wise.sgm.model.domain.ImportMaillingFile;
import com.wise.sgm.model.dto.cancellation.CancellationDTO;
import com.wise.sgm.model.dto.dataControl.DataControlDTO;
import com.wise.sgm.model.enums.ImportStatusEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ImportMaillingFileDTO {

    private Long id;

    private Date startDate;

    private Date endDate;

    private String nameFile;

    private ImportStatusEnum importStatusEnum;

    private String error;

    private CancellationDTO cancellation;

    private DataControlDTO dataControl;



    public static ImportMaillingFile convertToEntity(ImportMaillingFileDTO dto) {
        ImportMaillingFile ret = new ImportMaillingFile();
        ret.setId(dto.getId());
        ret.setStartDate(dto.getStartDate());
        ret.setNameFile(dto.getNameFile());
        ret.setImportStatusEnum(dto.getImportStatusEnum());
        ret.setError(dto.getError());
        if(dto.getCancellation().getCancellationDate() != null){
            ret.setCancellation(CancellationDTO.convertToEntity(dto.getCancellation()));
        }
        if(dto.getDataControl() != null) {
            ret.setDataControl(DataControlDTO.convertToEntity(dto.getDataControl()));
        }
        return ret;
    }

    public static ImportMaillingFileDTO convertToDTO(ImportMaillingFile entity) {
        ImportMaillingFileDTO ret = new ImportMaillingFileDTO();
        ret.setId(entity.getId());
        ret.setStartDate(entity.getStartDate());
        ret.setNameFile(entity.getNameFile());
        ret.setImportStatusEnum(entity.getImportStatusEnum());
        ret.setError(entity.getError());
        if(entity.getCancellation().getCancellationDate() != null){
            ret.setCancellation(CancellationDTO.convertToDTO(entity.getCancellation()));
        }
        if(entity.getDataControl() != null) {
            ret.setDataControl(DataControlDTO.convertToDTO(entity.getDataControl()));
        }
        return ret;
    }

    public static List<ImportMaillingFileDTO> convertToListDTO(List<ImportMaillingFile> entitys) {
        List<ImportMaillingFileDTO> ret = new ArrayList<ImportMaillingFileDTO>();
        for (ImportMaillingFile entity : entitys) {
            ret.add(ImportMaillingFileDTO.convertToDTO(entity));
        }
        return ret;
    }

    public static List<ImportMaillingFile> convertToListEntity(List<ImportMaillingFileDTO> DTOs) {
        List<ImportMaillingFile> ret = new ArrayList<ImportMaillingFile>();
        for (ImportMaillingFileDTO dto : DTOs) {
            ret.add(ImportMaillingFileDTO.convertToEntity(dto));
        }
        return ret;
    }


}

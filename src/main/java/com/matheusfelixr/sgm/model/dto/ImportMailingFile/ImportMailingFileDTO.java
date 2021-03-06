package com.matheusfelixr.sgm.model.dto.ImportMailingFile;

import com.matheusfelixr.sgm.model.domain.ImportMailingFile;
import com.matheusfelixr.sgm.model.dto.cancellation.CancellationDTO;
import com.matheusfelixr.sgm.model.dto.dataControl.DataControlDTO;
import com.matheusfelixr.sgm.model.enums.TransactionsStatusEnum;
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

    private TransactionsStatusEnum transactionsStatusEnum;

    private String error;

    private CancellationDTO cancellation;

    private DataControlDTO dataControl;



    public static ImportMailingFile convertToEntity(ImportMailingFileDTO dto) {
        ImportMailingFile ret = new ImportMailingFile();
        ret.setId(dto.getId());
        ret.setStartDate(dto.getStartDate());
        ret.setNameFile(dto.getNameFile());
        ret.setTransactionsStatus(dto.getTransactionsStatusEnum());
        ret.setError(dto.getError());
        if(dto.getCancellation().getCancellationDate() != null){
            ret.setCancellation(CancellationDTO.convertToEntity(dto.getCancellation()));
        }
        if(dto.getDataControl() != null) {
            ret.setDataControl(DataControlDTO.convertToEntity(dto.getDataControl()));
        }
        return ret;
    }

    public static ImportMailingFileDTO convertToDTO(ImportMailingFile entity) {
        ImportMailingFileDTO ret = new ImportMailingFileDTO();
        ret.setId(entity.getId());
        ret.setStartDate(entity.getStartDate());
        ret.setNameFile(entity.getNameFile());
        ret.setTransactionsStatusEnum(entity.getTransactionsStatus());
        ret.setError(entity.getError());
        if(entity.getCancellation().getCancellationDate() != null){
            ret.setCancellation(CancellationDTO.convertToDTO(entity.getCancellation()));
        }
        if(entity.getDataControl() != null) {
            ret.setDataControl(DataControlDTO.convertToDTO(entity.getDataControl()));
        }
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

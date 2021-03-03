package com.matheusfelixr.sgm.model.dto.ImportMailingFile;

import lombok.Data;

@Data
public class DownloadFileImportMailingFileDTO {

    private byte[] file;

    public DownloadFileImportMailingFileDTO(byte[] file) {
        this.file = file;
    }
}

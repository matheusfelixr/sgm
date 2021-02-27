package com.wise.sgm.model.dto.ImportMaillingFile;

import lombok.Data;

@Data
public class DownloadFileImportMaillingFileDTO {

    private byte[] file;

    public DownloadFileImportMaillingFileDTO(byte[] file) {
        this.file = file;
    }
}

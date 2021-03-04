package com.matheusfelixr.sgm.service;

import com.matheusfelixr.sgm.model.domain.ExportMailingFile;
import com.matheusfelixr.sgm.model.domain.Mailing;
import com.matheusfelixr.sgm.model.domain.UserAuthentication;
import com.matheusfelixr.sgm.model.dto.MessageDTO;
import com.matheusfelixr.sgm.repository.ExportMailingFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExportMailingFileService {

    @Autowired
    private ExportMailingFileRepository exportMailingFileRepository;

    @Autowired
    private MailingService mailingService;

    public MessageDTO generateFile(ExportMailingFile exportMailingFile, UserAuthentication currentUser) throws Exception {
        exportMailingFile.setStartDate(new Date());

        List<Mailing> mailings = mailingService.findAttendanceByStartDateAndEndDate(exportMailingFile.getStartDateExport(), exportMailingFile.getEndDateExport());
       // AAAAMMDD_RETORNO_WISE.txt
        String dateFormat = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        dateFormat = sdf.format(new Date());

        FileWriter arq = new FileWriter(dateFormat + "_RETORNO_WISE.txt");
        PrintWriter printWriter = new PrintWriter(arq);
        printWriter.println("teste");

        arq.close();




        exportMailingFile.setEndDate(new Date());
        this.save(exportMailingFile);
        return new MessageDTO("opaOpa");
    }

    public ExportMailingFile save(ExportMailingFile exportMailingFile) {
        return this.exportMailingFileRepository.save(exportMailingFile);
    }
}

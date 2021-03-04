package com.matheusfelixr.sgm.service;

import com.matheusfelixr.sgm.model.domain.ExportMailingFile;
import com.matheusfelixr.sgm.model.domain.Mailing;
import com.matheusfelixr.sgm.model.domain.UserAuthentication;
import com.matheusfelixr.sgm.model.dto.MessageDTO;
import com.matheusfelixr.sgm.model.enums.TypeExportedEnum;
import com.matheusfelixr.sgm.repository.ExportMailingFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
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
        exportMailingFile.getDataControl().markCreate(currentUser);
        List<Mailing> mailings = mailingService.findAttendanceByStartDateAndEndDate(exportMailingFile.getStartDateExport(), exportMailingFile.getEndDateExport());
        if(mailings.isEmpty()){
            throw new ValidationException("Nao encontrado mailings");
        }
        // AAAAMMDD_RETORNO_WISE.txt
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateFormat = sdf.format(new Date());

        FileWriter arq = new FileWriter(dateFormat + "_RETORNO_WISE.txt");
        PrintWriter printWriter = new PrintWriter(arq);
        printWriter.println("COD_CAMPANHA|COD_CLIENTE|NUM_CPF_CNPJ|NOME_CLIENTE|TELEFONE_TENTATIVA_CONTATO|DES_MOTIVO_FINALIZACAO|DES_SUB_MOTIVO_FINALIZACAO|ELEGIVEL|DT_CONTATO|CAMPANHA");

        for (Mailing mailing : mailings) {
            if (isPrint(mailing, exportMailingFile)) {
                printWriter.println(mailing.getMailingLayout1().getCODCAMPANHA() + "|"
                        + mailing.getMailingLayout1().getCUSTOMER_KEY() + "|"
                        + mailing.getMailingLayout1().getCPF_CNPJ() + "|"
                        + mailing.getMailingLayout1().getNOME() + "|"
                        + mailing.getMailingLayout1().getTELEFONE_CONTATO_1() + "|"
                        + mailing.getMailingStatus().getReasonMailing().getDescription() + "|"
                        + mailing.getMailingStatus().getDescription() + "|"
                        + "|"
                        + mailing.getEndDate() + "|"
                        + mailing.getMailingLayout1().getCAMPANHA());
            }
            mailing.getExportMailingFiles().add(exportMailingFile);
        }
        arq.close();

        File file = new File(dateFormat + "_RETORNO_WISE.txt");
        exportMailingFile.setFile(Files.readAllBytes(file.toPath()));
        file.delete();

        exportMailingFile.setEndDate(new Date());
        this.save(exportMailingFile);
        this.mailingService.saveAll(mailings);
        return new MessageDTO("opaOpa");
    }

    public  boolean isPrint(Mailing mailing,ExportMailingFile exportMailingFile){
        // Se quiser os que nao foram exportados
        if(mailing.getExportMailingFiles().isEmpty() && exportMailingFile.getTypeExported() == TypeExportedEnum.NOT_EXPORTED){
            return true;
            //se quiser os que foram exportados
        } else if( mailing.getExportMailingFiles().size() > 0 && exportMailingFile.getTypeExported() == TypeExportedEnum.ALREADY_EXPORTED) {
            return true;
            //se for aparecer ambos
        }else if (exportMailingFile.getTypeExported() == TypeExportedEnum.BOTH){
            return true;
        }

      return false;
    }

    public ExportMailingFile save(ExportMailingFile exportMailingFile) {
        return this.exportMailingFileRepository.save(exportMailingFile);
    }
}

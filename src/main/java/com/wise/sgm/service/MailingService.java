package com.wise.sgm.service;

import com.wise.sgm.model.domain.ImportMailingFile;
import com.wise.sgm.model.domain.Mailing;
import com.wise.sgm.model.domain.MailingType;
import com.wise.sgm.model.domain.mailingLayouts.MailingLayout1;
import com.wise.sgm.repository.MailingRepository;
import com.wise.sgm.service.mailingLayouts.MailingLayout1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MailingService {

    @Autowired
    private MailingRepository mailingRepository;

    @Autowired
    private MailingLayout1Service mailingLayout1Service;

    public Mailing getNextMailing() throws Exception{
        List<Mailing> mailings = this.mailingRepository.findByMailingStatusIsNullAndDateSentToUserIsNullOrderByDataControlCreateDate();
            if(mailings.isEmpty()){
                throw new ValidationException("Não possui novo mailing");
            }
        Mailing mailing = mailings.get(0);
        mailing.setDateSentToUser(new Date());
        mailing.setStartDate(new Date());
        mailingRepository.saveAndFlush(mailing);
        return mailing;
    }

    public List<Mailing> findNotAtended(){
        return mailingRepository.findAll();
    }

    public List<Mailing> findAll(){
        return mailingRepository.findAll();
    }

    public void importMailing(MailingType mailingType, MultipartFile multipartFile, ImportMailingFile importMailingFile) throws Exception {
        // melhorar forma de pegar layout
        final String layout1 = "CODCAMPANHA|CUSTOMER_KEY|CPF CNPJ|NOME|TELEFONE_CONTATO_1|TELEFONE_CONTATO_2|TELEFONE_CONTATO_3|INFORMAÇÕES ADICIONAIS|OFERTA_1|OFERTA_2|OFERTA_3|OFERTA_1_CONDICIONAL|OFERTA_2_CONDICIONAL|NUMERO OPP|SUBSCRIÇÃO|CIDADE|REGIONAL|CAMPANHA";

        switch (mailingType.getLayout()) {
            case layout1:
                createMailingLayout1(mailingType, multipartFile, importMailingFile);
                break;
            default:
                throw new ValidationException("Não foi encontrado layout procure o desenvolvedor");
        }

    }

    private void createMailingLayout1(MailingType mailingType, MultipartFile multipartFile, ImportMailingFile importMailingFile) throws Exception {
        java.io.File file = new java.io.File(multipartFile.getOriginalFilename());
        FileOutputStream in = new FileOutputStream(file);
        in.write(multipartFile.getBytes());
        in.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), "ISO-8859-1"));
        Object[] lines = br.lines().toArray();


        for (Object line : lines) {

            if (!Arrays.stream(lines).findFirst().get().equals(line)) {
                MailingLayout1 mailingLayout1 = new MailingLayout1();
                mailingLayout1.getDataControl().markCreate();
                mailingLayout1.setMailingType(mailingType);
                mailingLayout1.setImportMailingFile(importMailingFile);

                Mailing mailing = new Mailing();
                mailing.getDataControl().markCreate();

                int index = 0;
                int skip = 0;
                int nextPipe = 0;
                String lineFor = line.toString();
                for (skip = 0; skip < 1; ) {

                    nextPipe = lineFor.indexOf("|");
                    if (nextPipe == -1) {
                        // setar o ultimo
                        mailingLayout1.setCAMPANHA(lineFor.substring(0, lineFor.length()));
                        skip = 1;
                    } else {

                        switch (index) {
                                //CODCAMPANHA
                            case 0:
                                mailingLayout1.setCODCAMPANHA(lineFor.substring(0, nextPipe));
                                break;
                                //CUSTOMER_KEY
                            case 1:
                                mailingLayout1.setCUSTOMER_KEY(lineFor.substring(0, nextPipe));
                                break;
                                //CPF CNPJ
                            case 2:
                                mailingLayout1.setCPF_CNPJ(lineFor.substring(0, nextPipe));
                                break;
                                //NOME
                            case 3:
                                mailingLayout1.setNOME(lineFor.substring(0, nextPipe));
                                break;
                            case 4:
                                mailingLayout1.setTELEFONE_CONTATO_1(lineFor.substring(0, nextPipe));
                                break;
                            case 5:
                                mailingLayout1.setTELEFONE_CONTATO_2(lineFor.substring(0, nextPipe));
                                break;
                            case 6:
                                mailingLayout1.setTELEFONE_CONTATO_3(lineFor.substring(0, nextPipe));
                                break;
                            case 7:
                                mailingLayout1.setINFORMAÇÕES_ADICIONAIS(lineFor.substring(0, nextPipe));
                                break;
                            case 8:
                                mailingLayout1.setOFERTA_1(lineFor.substring(0, nextPipe));
                                break;
                            case 9:
                                mailingLayout1.setOFERTA_2(lineFor.substring(0, nextPipe));
                                break;
                            case 10:
                                mailingLayout1.setOFERTA_3(lineFor.substring(0, nextPipe));
                                break;
                            case 11:
                                mailingLayout1.setOFERTA_1_CONDICIONAL(lineFor.substring(0, nextPipe));
                                break;
                            case 12:
                                mailingLayout1.setOFERTA_2_CONDICIONAL(lineFor.substring(0, nextPipe));
                                break;
                            case 13:
                                mailingLayout1.setNUMERO_OPP(lineFor.substring(0, nextPipe));
                                break;
                            case 14:
                                mailingLayout1.setSUBSCRIÇÃO(lineFor.substring(0, nextPipe));
                                break;
                            case 15:
                                mailingLayout1.setCIDADE(lineFor.substring(0, nextPipe));
                                break;
                            case 16:
                                mailingLayout1.setREGIONAL(lineFor.substring(0, nextPipe));
                                break;
                        }

                        lineFor = lineFor.substring(nextPipe + 1, lineFor.length());
                    }

                    index++;
                }
                MailingLayout1 save = this.mailingLayout1Service.save(mailingLayout1);
                mailing.setMailingLayout1(save);
                this.save(mailing);
            }
        }
        br.close();
        file.delete();
    }

    public Mailing save(Mailing mailing) throws Exception{
        return this.mailingRepository.save(mailing);
    }

}
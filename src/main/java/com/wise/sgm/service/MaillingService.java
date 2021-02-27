package com.wise.sgm.service;

import com.wise.sgm.model.domain.ImportMaillingFile;
import com.wise.sgm.model.domain.Mailling;
import com.wise.sgm.model.domain.MaillingType;
import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.domain.maillingLayouts.MaillingLayout1;
import com.wise.sgm.repository.MaillingRepository;
import com.wise.sgm.service.maillingLayouts.MaillingLayout1Service;
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
public class MaillingService {

    @Autowired
    private MaillingRepository maillingRepository;

    @Autowired
    private MaillingLayout1Service maillingLayout1Service;

    public Mailling getNextMailling(UserAuthentication currentUser) throws Exception{
        List<Mailling> maillings = this.maillingRepository.findByMaillingStatusIsNullAndDateSentToUserIsNullOrderByDataControlCreateDate();
            if(maillings.isEmpty()){
                throw new ValidationException("Não possui novo mailling");
            }
        Mailling mailling = maillings.get(0);
        mailling.setDateSentToUser(new Date());
        mailling.setStartDate(new Date());
        mailling.getDataControl().markModified(currentUser);
        maillingRepository.saveAndFlush(mailling);
        return mailling;
    }

    public List<Mailling> findAll(){
        return maillingRepository.findAll();
    }

    public void importMailling(MaillingType maillingType, MultipartFile multipartFile, ImportMaillingFile importMaillingFile, UserAuthentication currentUser) throws Exception {
        // melhorar forma de pegar layout
        final String layout1 = "CODCAMPANHA|CUSTOMER_KEY|CPF CNPJ|NOME|TELEFONE_CONTATO_1|TELEFONE_CONTATO_2|TELEFONE_CONTATO_3|INFORMAÇÕES ADICIONAIS|OFERTA_1|OFERTA_2|OFERTA_3|OFERTA_1_CONDICIONAL|OFERTA_2_CONDICIONAL|NUMERO OPP|SUBSCRIÇÃO|CIDADE|REGIONAL|CAMPANHA";

        switch (maillingType.getLayout()) {
            case layout1:
                createMaillingLayout1(maillingType, multipartFile, importMaillingFile,currentUser);
                break;
            default:
                throw new ValidationException("Não foi encontrado layout procure o desenvolvedor");
        }

    }

    private void createMaillingLayout1(MaillingType maillingType, MultipartFile multipartFile, ImportMaillingFile importMaillingFile, UserAuthentication currentUser) throws Exception {
        java.io.File file = new java.io.File(multipartFile.getOriginalFilename());
        FileOutputStream in = new FileOutputStream(file);
        in.write(multipartFile.getBytes());
        in.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), "ISO-8859-1"));
        Object[] lines = br.lines().toArray();


        for (Object line : lines) {

            if (!Arrays.stream(lines).findFirst().get().equals(line)) {
                MaillingLayout1 maillingLayout1 = new MaillingLayout1();
                maillingLayout1.getDataControl().markCreate(currentUser);
                maillingLayout1.setMaillingType(maillingType);
                maillingLayout1.setImportMaillingFile(importMaillingFile);

                Mailling mailling = new Mailling();
                mailling.getDataControl().markCreate(currentUser);

                int index = 0;
                int skip = 0;
                int nextPipe = 0;
                String lineFor = line.toString();
                for (skip = 0; skip < 1; ) {

                    nextPipe = lineFor.indexOf("|");
                    if (nextPipe == -1) {
                        // setar o ultimo
                        maillingLayout1.setCAMPANHA(lineFor.substring(0, lineFor.length()));
                        skip = 1;
                    } else {

                        switch (index) {
                                //CODCAMPANHA
                            case 0:
                                maillingLayout1.setCODCAMPANHA(lineFor.substring(0, nextPipe));
                                break;
                                //CUSTOMER_KEY
                            case 1:
                                maillingLayout1.setCUSTOMER_KEY(lineFor.substring(0, nextPipe));
                                break;
                                //CPF CNPJ
                            case 2:
                                maillingLayout1.setCPF_CNPJ(lineFor.substring(0, nextPipe));
                                break;
                                //NOME
                            case 3:
                                maillingLayout1.setNOME(lineFor.substring(0, nextPipe));
                                break;
                            case 4:
                                maillingLayout1.setTELEFONE_CONTATO_1(lineFor.substring(0, nextPipe));
                                break;
                            case 5:
                                maillingLayout1.setTELEFONE_CONTATO_2(lineFor.substring(0, nextPipe));
                                break;
                            case 6:
                                maillingLayout1.setTELEFONE_CONTATO_3(lineFor.substring(0, nextPipe));
                                break;
                            case 7:
                                maillingLayout1.setINFORMAÇÕES_ADICIONAIS(lineFor.substring(0, nextPipe));
                                break;
                            case 8:
                                maillingLayout1.setOFERTA_1(lineFor.substring(0, nextPipe));
                                break;
                            case 9:
                                maillingLayout1.setOFERTA_2(lineFor.substring(0, nextPipe));
                                break;
                            case 10:
                                maillingLayout1.setOFERTA_3(lineFor.substring(0, nextPipe));
                                break;
                            case 11:
                                maillingLayout1.setOFERTA_1_CONDICIONAL(lineFor.substring(0, nextPipe));
                                break;
                            case 12:
                                maillingLayout1.setOFERTA_2_CONDICIONAL(lineFor.substring(0, nextPipe));
                                break;
                            case 13:
                                maillingLayout1.setNUMERO_OPP(lineFor.substring(0, nextPipe));
                                break;
                            case 14:
                                maillingLayout1.setSUBSCRIÇÃO(lineFor.substring(0, nextPipe));
                                break;
                            case 15:
                                maillingLayout1.setCIDADE(lineFor.substring(0, nextPipe));
                                break;
                            case 16:
                                maillingLayout1.setREGIONAL(lineFor.substring(0, nextPipe));
                                break;
                        }

                        lineFor = lineFor.substring(nextPipe + 1, lineFor.length());
                    }

                    index++;
                }
                MaillingLayout1 save = this.maillingLayout1Service.save(maillingLayout1);
                mailling.setMaillingLayout1(save);
                this.save(mailling);
            }
        }
        br.close();
        file.delete();
    }

    public Mailling save(Mailling mailling) throws Exception{
        return this.maillingRepository.save(mailling);
    }

}
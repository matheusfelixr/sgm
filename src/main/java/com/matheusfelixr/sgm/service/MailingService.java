package com.matheusfelixr.sgm.service;

import com.matheusfelixr.sgm.model.domain.*;
import com.matheusfelixr.sgm.model.domain.mailingLayouts.MailingLayout1;
import com.matheusfelixr.sgm.model.dto.MessageDTO;
import com.matheusfelixr.sgm.repository.MailingRepository;
import com.matheusfelixr.sgm.service.mailingLayouts.MailingLayout1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class MailingService {

    @Autowired
    private MailingRepository mailingRepository;

    @Autowired
    private MailingLayout1Service mailingLayout1Service;

    @Autowired
    private MailingStatusService mailingStatusService;

    public Mailing getNextMailing(UserAuthentication currentUser) throws Exception {
        List<Mailing> ret = new ArrayList<>();
        List<Mailing> mailingsNotAttendeds = this.mailingRepository.findBySentToUserAndMailingStatusIsNullAndDateSentToUserIsNotNullAndCancellationCancellationDateIsNullOrderByDataControlCreateDate(currentUser);

        if (mailingsNotAttendeds.isEmpty()) {
            ret = this.mailingRepository.findNextAttendance();
        } else {
            ret = mailingsNotAttendeds;
        }
        if (ret.isEmpty()) {
            throw new ValidationException("Não possui novo mailing");
        }
        Mailing mailing = ret.get(0);
        mailing.setStartDate(new Date());
        mailing.getDataControl().markModified(currentUser);
        mailing.setDateSentToUser(new Date());
        mailing.setSentToUser(currentUser);
        mailingRepository.saveAndFlush(mailing);
        return mailing;
    }

    public List<Mailing> findAll() {
        return mailingRepository.findAll();
    }

    public void importMailing(MailingType mailingType, MultipartFile multipartFile, ImportMailingFile importMailingFile, UserAuthentication currentUser) throws Exception {
        // melhorar forma de pegar layout
        final String layout1 = "CODCAMPANHA|CUSTOMER_KEY|CPF CNPJ|NOME|TELEFONE_CONTATO_1|TELEFONE_CONTATO_2|TELEFONE_CONTATO_3|INFORMAÇÕES ADICIONAIS|OFERTA_1|OFERTA_2|OFERTA_3|OFERTA_1_CONDICIONAL|OFERTA_2_CONDICIONAL|NUMERO OPP|SUBSCRIÇÃO|CIDADE|REGIONAL|CAMPANHA";

        switch (mailingType.getLayout()) {
            case layout1:
                createMailingLayout1(mailingType, multipartFile, importMailingFile, currentUser, mailingType.getNumberOfFields());
                break;
            default:
                throw new ValidationException("Não foi encontrado layout procure o desenvolvedor");
        }

    }

    private void createMailingLayout1(MailingType mailingType, MultipartFile multipartFile, ImportMailingFile importMailingFile, UserAuthentication currentUser, int numberOfFields) throws Exception {

        java.io.File file = new java.io.File(multipartFile.getOriginalFilename());
        FileOutputStream in = new FileOutputStream(file);
        in.write(multipartFile.getBytes());
        in.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), "ISO-8859-1"));
        Object[] lines = br.lines().toArray();

        System.out.println("Quantidade de linhas: " + lines.length);
        try {
            for (Object line : lines) {

                if (!Arrays.stream(lines).findFirst().get().equals(line)) {
                    MailingLayout1 mailingLayout1 = new MailingLayout1();
                    mailingLayout1.getDataControl().markCreate(currentUser);
                    mailingLayout1.setMailingType(mailingType);
                    mailingLayout1.setImportMailingFile(importMailingFile);

                    Mailing mailing = new Mailing();
                    mailing.getDataControl().markCreate(currentUser);

                    int numberOfColumns = 0;
                    int index = 0;
                    int skip = 0;
                    int nextPipe = 0;
                    String lineFor = line.toString();
                    for (skip = 0; skip < 1; ) {
                        numberOfColumns++;
                        nextPipe = lineFor.indexOf("|");
                        if (nextPipe == -1) {
                            if (numberOfFields != numberOfColumns) {
                                throw new ValidationException("Numero de colunas diferente do descrito no layout. Linha: " + line.toString());
                            }
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
                    MailingLayout1 save = this.mailingLayout1Service.saveNew(mailingLayout1);
                    mailing.setMailingLayout1(save);
                    this.save(mailing);
                }
            }
            br.close();
            file.delete();
        } catch (ValidationException e) {
            br.close();
            file.delete();
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            br.close();
            file.delete();
            throw new Exception(e);
        }
    }

    public Mailing save(Mailing mailing) throws Exception {
        return this.mailingRepository.save(mailing);
    }

    public void releasesMillings() throws Exception {
        List<Mailing> mailingsNotAttendeds = this.mailingRepository.findBySentToUserIsNullAndMailingStatusIsNullAndDateSentToUserIsNotNullAndCancellationCancellationDateIsNullOrderByDataControlCreateDate();
        for (Mailing mailing : mailingsNotAttendeds) {
            mailing.setSentToUser(null);
            mailing.getDataControl().markModified(new UserAuthentication(1L));
            this.save(mailing);
        }
    }

    public Optional<Mailing> findById(Long idMailing) {
        return mailingRepository.findById(idMailing);
    }

    public MessageDTO saveAttendance(Long idMailingStatus, Long idMailing, UserAuthentication currentUser) throws Exception {
        Optional<MailingStatus> mailingStatus = mailingStatusService.findById(idMailingStatus);
        Optional<Mailing> mailing = this.findById(idMailing);
        validateSaveAttendance(mailingStatus, mailing);

        mailing.get().setEndDate(new Date());
        mailing.get().setMailingStatus(mailingStatus.get());
        mailing.get().getDataControl().markModified(currentUser);
        this.save(mailing.get());
        return new MessageDTO("Sucesso ao salvar atendimento");
    }

    private void validateSaveAttendance(Optional<MailingStatus> mailingStatus, Optional<Mailing> mailing) throws ValidationException {
        if (!mailingStatus.isPresent()) {
            throw new ValidationException("Não foi possivel encontrar o status. Procure o suporte.");
        }
        if (!mailing.isPresent()) {
            throw new ValidationException("Não foi possivel encontrar o mailing. Procure o suporte.");
        }
        if (mailing.get().getMailingStatus() != null) {
            throw new ValidationException("Status já salvo. Favor printar tela e  atualize. Favor informar ao suporte.");
        }

    }

    public List<Mailing> findByMailingLayout1(MailingLayout1 mailingLayout1) {
        return this.mailingRepository.findByMailingLayout1(mailingLayout1);
    }

}
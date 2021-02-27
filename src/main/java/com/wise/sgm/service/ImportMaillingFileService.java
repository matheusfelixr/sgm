package com.wise.sgm.service;

import com.wise.sgm.model.domain.ImportMaillingFile;
import com.wise.sgm.model.domain.MaillingType;
import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.enums.ImportStatusEnum;
import com.wise.sgm.repository.ImportMaillingFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImportMaillingFileService {

    @Autowired
    private ImportMaillingFileRepository importMaillingFileRepository;

    @Autowired
    private MaillingTypeService maillingTypeService;

    @Autowired
    private MaillingService maillingService;

    public ImportMaillingFile importFile(MultipartFile multipartFile, UserAuthentication currentUser) throws Exception {
        ImportMaillingFile importMaillingFile = new ImportMaillingFile();
        importMaillingFile.getDataControl().markCreate(currentUser);
        importMaillingFile.setStartDate(new Date());

        try {
            if(multipartFile.isEmpty()){
                throw new ValidationException("Arquivo não encontrado");
            }
            importMaillingFile.setFile(multipartFile.getBytes());
            importMaillingFile.setNameFile(multipartFile.getOriginalFilename());

            this.verifyRegisteredFile(multipartFile.getBytes());

            Optional<MaillingType> maillingType = this.maillingTypeService.existLayoutByMultipartFile(multipartFile);

            if(!maillingType.isPresent()){
                throw new ValidationException("Layout não encontrado. Verifique o arquivo caso o erro permaneça contate o desenvolvedor.");
            }

            importMaillingFile.setEndDate(new Date());
            importMaillingFile.setImportStatusEnum(ImportStatusEnum.SUCCESS);

            ImportMaillingFile ret = this.save(importMaillingFile);
            this.maillingService.importMailling(maillingType.get(), multipartFile, ret, currentUser);

            return ret;
        }catch (ValidationException e){
            e.printStackTrace();
            importMaillingFile.setError(e.getMessage());
            importMaillingFile.setEndDate(new Date());
            importMaillingFile.setImportStatusEnum(ImportStatusEnum.FAIL);
            this.save(importMaillingFile);
            throw new ValidationException(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            importMaillingFile.setError(e.getMessage());
            importMaillingFile.setEndDate(new Date());
            importMaillingFile.setImportStatusEnum(ImportStatusEnum.FAIL);
            this.save(importMaillingFile);
            throw new ValidationException("Erro ao importar arquivo");
        }
    }

    public ImportMaillingFile save(ImportMaillingFile importMaillingFile){
        return importMaillingFileRepository.save(importMaillingFile);
    }

    public List<ImportMaillingFile> findAll() throws Exception {
        List<ImportMaillingFile> all = importMaillingFileRepository.findAll();
        all.forEach(a-> a.setFile(null));
        return all;
    }

    public Optional<ImportMaillingFile> findById(Long id) throws Exception {
        return importMaillingFileRepository.findById(id);
    }

    public List<ImportMaillingFile> findByExample(ImportMaillingFile importMaillingFile) throws Exception {

        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        Example<ImportMaillingFile> example = Example.<ImportMaillingFile>of(importMaillingFile, matcher);

        return importMaillingFileRepository.findAll(example);
    }

    public ImportMaillingFile cancel(Long id, UserAuthentication currentUser) throws Exception {
        Optional<ImportMaillingFile> importMaillingFile = importMaillingFileRepository.findById(id);
        if(!importMaillingFile.isPresent()){
            throw new ValidationException("Não encontrado import de arquivo com esse id");
        }
        if(importMaillingFile.get().getCancellation().getCancellationDate() != null){
            throw new ValidationException("Import de arquivo ja cancelado");
        }
        importMaillingFile.get().getCancellation().markCanceled("Solicitação de cancelamento", currentUser);
        importMaillingFile.get().getDataControl().markModified(currentUser);
        //cancelar import realizado de arquivo
        return this.save(importMaillingFile.get());
    }

    public void verifyRegisteredFile(byte[] file) throws Exception {
        ImportMaillingFile importMaillingFile = new ImportMaillingFile();
        importMaillingFile.setFile(file);
        importMaillingFile.setImportStatusEnum(ImportStatusEnum.SUCCESS);
        importMaillingFile.setCancellation(null);

        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        Example<ImportMaillingFile> example = Example.<ImportMaillingFile>of(importMaillingFile, matcher);
        List<ImportMaillingFile> all = importMaillingFileRepository.findAll(example);

        if(!all.isEmpty()){
            throw new ValidationException("Arquivo já importado anteriormente");
        }
    }

}

package com.wise.sgm.service;

import com.wise.sgm.model.domain.ImportMailingFile;
import com.wise.sgm.model.enums.ImportStatus;
import com.wise.sgm.repository.ImportMailingFileRepository;
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
public class ImportMailingFileService {

    @Autowired
    private ImportMailingFileRepository importMailingFileRepository;

    public ImportMailingFile importFile(MultipartFile multipartFile) throws Exception {
        ImportMailingFile importMailingFile = new ImportMailingFile();
        importMailingFile.getDataControl().markCreate();
        importMailingFile.setStartDate(new Date());

        try {
            if(multipartFile.isEmpty()){
                throw new ValidationException("Arquivo não encontrado");
            }
            importMailingFile.setFile(multipartFile.getBytes());
            importMailingFile.setNameFile(multipartFile.getOriginalFilename());

            this.verifyRegisteredFile(multipartFile.getBytes());

            //import linhas da tabela


            importMailingFile.setEndDate(new Date());
            importMailingFile.setImportStatus(ImportStatus.SUCCESS);

            return this.save(importMailingFile);
        }catch (ValidationException e){
            e.printStackTrace();
            importMailingFile.setError(e.getMessage());
            importMailingFile.setEndDate(new Date());
            importMailingFile.setImportStatus(ImportStatus.FAIL);
            this.save(importMailingFile);
            throw new ValidationException(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            importMailingFile.setError(e.getMessage());
            importMailingFile.setEndDate(new Date());
            importMailingFile.setImportStatus(ImportStatus.FAIL);
            this.save(importMailingFile);
            throw new ValidationException("Erro ao importar arquivo");
        }
    }

    public ImportMailingFile save(ImportMailingFile importMailingFile){
        return importMailingFileRepository.save(importMailingFile);
    }

    public List<ImportMailingFile> findAll() throws Exception {
        List<ImportMailingFile> all = importMailingFileRepository.findAll();
        all.forEach(a-> a.setFile(null));
        return all;
    }

    public Optional<ImportMailingFile> findById(Long id) throws Exception {
        return importMailingFileRepository.findById(id);
    }

    public List<ImportMailingFile> findByExample(ImportMailingFile importMailingFile) throws Exception {

        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        Example<ImportMailingFile> example = Example.<ImportMailingFile>of(importMailingFile, matcher);

        return importMailingFileRepository.findAll(example);
    }

    public ImportMailingFile cancel(Long id) throws Exception {
        Optional<ImportMailingFile> importMailingFile = importMailingFileRepository.findById(id);
        if(!importMailingFile.isPresent()){
            throw new ValidationException("Não encontrado import de arquivo com esse id");
        }
        if(importMailingFile.get().getCancellation().getCancellationDate() != null){
            throw new ValidationException("Import de arquivo ja cancelado");
        }
        importMailingFile.get().getCancellation().markCanceled("Solicitação de cancelamento");
        importMailingFile.get().getDataControl().markModified();
        //cancelar import realizado de arquivo
        return this.save(importMailingFile.get());
    }

    public void verifyRegisteredFile(byte[] file) throws Exception {
        ImportMailingFile importMailingFile = new ImportMailingFile();
        importMailingFile.setFile(file);
        importMailingFile.setImportStatus(ImportStatus.SUCCESS);
        importMailingFile.setCancellation(null);

        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        Example<ImportMailingFile> example = Example.<ImportMailingFile>of(importMailingFile, matcher);
        List<ImportMailingFile> all = importMailingFileRepository.findAll(example);

        if(!all.isEmpty()){
            throw new ValidationException("Arquivo já importado anteriormente");
        }
    }



//    java.io.File files = new java.io.File("D:\\opaopa.txt");
//    FileOutputStream in = new FileOutputStream(files) ;
//            in.write(importMailingInfo.getFile());
//            in.close();

}

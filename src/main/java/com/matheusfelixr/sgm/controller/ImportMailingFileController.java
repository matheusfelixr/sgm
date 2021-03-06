package com.matheusfelixr.sgm.controller;

import com.matheusfelixr.sgm.model.domain.UserAuthentication;
import com.matheusfelixr.sgm.model.domain.ImportMailingFile;
import com.matheusfelixr.sgm.model.dto.ImportMailingFile.DownloadFileImportMailingFileDTO;
import com.matheusfelixr.sgm.model.dto.ImportMailingFile.ImportMailingFileDTO;
import com.matheusfelixr.sgm.model.dto.config.ResponseApi;
import com.matheusfelixr.sgm.service.ImportMailingFileService;
import com.matheusfelixr.sgm.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/import-mailing-file")
public class ImportMailingFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportMailingFileController.class);

    @Autowired
    private ImportMailingFileService importMailingFileService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value  = "/import-file")
    public ResponseEntity<ResponseApi<ImportMailingFileDTO>> importFile(@RequestParam("file") MultipartFile file) {
        LOGGER.info("Inicio processo de importacao de arquivo.");
        ResponseApi<ImportMailingFileDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();
            response.setData(ImportMailingFileDTO.convertToDTO(this.importMailingFileService.importFile(file, currentUser)));
            LOGGER.info("Importacao realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> errors = Arrays.asList(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar importacao");
            List<String> errors = Arrays.asList("Erro inesperado ao realizar importação");
            response.setErrors(errors);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping(value  = "/find-all")
    public ResponseEntity<ResponseApi<List<ImportMailingFileDTO>>> findAll()  {
        LOGGER.debug("Inicio processo de buscar todos os imports de arquivo.");
        ResponseApi<List<ImportMailingFileDTO>> response = new ResponseApi<>();
        try {
            response.setData(ImportMailingFileDTO.convertToListDTO(this.importMailingFileService.findAll()));
            LOGGER.debug("Processo de buscar todos os imports de arquivo realizado com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> errors = Arrays.asList(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar processo de buscar todos os imports de arquivo");
            List<String> errors = Arrays.asList("Erro inesperado ao realizar processo de buscar todos os imports de arquivo");
            response.setErrors(errors);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping(value  = "/cancel/{id}/{obs}")
    public ResponseEntity<ResponseApi<ImportMailingFileDTO>> cancel(@PathVariable(value = "id") Long id, @PathVariable(value = "obs") String observationCancel)  {
        LOGGER.debug("Inicio processo de cancelamento imports de arquivo.");
        ResponseApi<ImportMailingFileDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();
            response.setData(ImportMailingFileDTO.convertToDTO(this.importMailingFileService.cancel(id,observationCancel, currentUser)));
            LOGGER.debug("Cancelamento de import de arquivo realizado com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> errors = Arrays.asList(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar c ancelamento de import de arquivo");
            List<String> errors = Arrays.asList("Erro inesperado ao realizar c ancelamento de import de arquivo");
            response.setErrors(errors);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }


    @GetMapping(value  = "/download/{id}")
    public ResponseEntity<ResponseApi<DownloadFileImportMailingFileDTO>> download(@PathVariable(value = "id") Long id)  {
        LOGGER.debug("Inicio processo de cancelamento imports de arquivo.");
        ResponseApi<DownloadFileImportMailingFileDTO> response = new ResponseApi<>();
        try {
            Optional<ImportMailingFile> importMailingFile = this.importMailingFileService.findById(id);
            if(!importMailingFile.isPresent()){
                throw new ValidationException("Não encontrado import com codigo informado");
            }

            response.setData(new DownloadFileImportMailingFileDTO(importMailingFile.get().getFile()));
            LOGGER.debug("Cancelamento de import de arquivo realizado com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> errors = Arrays.asList(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar c ancelamento de import de arquivo");
            List<String> errors = Arrays.asList("Erro inesperado ao realizar c ancelamento de import de arquivo");
            response.setErrors(errors);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}

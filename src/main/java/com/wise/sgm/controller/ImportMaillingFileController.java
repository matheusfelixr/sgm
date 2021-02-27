package com.wise.sgm.controller;

import com.wise.sgm.model.domain.ImportMaillingFile;
import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.dto.ImportMaillingFile.DownloadFileImportMaillingFileDTO;
import com.wise.sgm.model.dto.ImportMaillingFile.ImportMaillingFileDTO;
import com.wise.sgm.model.dto.config.ResponseApi;
import com.wise.sgm.service.ImportMaillingFileService;
import com.wise.sgm.service.SecurityService;
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
@RequestMapping(path = "/import-mailling-file")
public class ImportMaillingFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportMaillingFileController.class);

    @Autowired
    private ImportMaillingFileService importMaillingFileService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value  = "/import-file")
    public ResponseEntity<ResponseApi<ImportMaillingFileDTO>> importFile(@RequestParam("file") MultipartFile file) {
        LOGGER.info("Inicio processo de importacao de arquivo.");
        ResponseApi<ImportMaillingFileDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();
            response.setData(ImportMaillingFileDTO.convertToDTO(this.importMaillingFileService.importFile(file, currentUser)));
            LOGGER.info("Importacao realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar importacao");
            List<String> erros = Arrays.asList("Erro inesperado ao realizar importação");
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping(value  = "/find-all")
    public ResponseEntity<ResponseApi<List<ImportMaillingFileDTO>>> findAll()  {
        LOGGER.debug("Inicio processo de buscar todos os imports de arquivo.");
        ResponseApi<List<ImportMaillingFileDTO>> response = new ResponseApi<>();
        try {
            response.setData(ImportMaillingFileDTO.convertToListDTO(this.importMaillingFileService.findAll()));
            LOGGER.debug("Processo de buscar todos os imports de arquivo realizado com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar processo de buscar todos os imports de arquivo");
            List<String> erros = Arrays.asList("Erro inesperado ao realizar processo de buscar todos os imports de arquivo");
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping(value  = "/cancel/{id}")
    public ResponseEntity<ResponseApi<ImportMaillingFileDTO>> cancel(@PathVariable(value = "id") Long id)  {
        LOGGER.debug("Inicio processo de cancelamento imports de arquivo.");
        ResponseApi<ImportMaillingFileDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();
            response.setData(ImportMaillingFileDTO.convertToDTO(this.importMaillingFileService.cancel(id, currentUser)));
            LOGGER.debug("Cancelamento de import de arquivo realizado com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar c ancelamento de import de arquivo");
            List<String> erros = Arrays.asList("Erro inesperado ao realizar c ancelamento de import de arquivo");
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }


    @GetMapping(value  = "/download/{id}")
    public ResponseEntity<ResponseApi<DownloadFileImportMaillingFileDTO>> download(@PathVariable(value = "id") Long id)  {
        LOGGER.debug("Inicio processo de cancelamento imports de arquivo.");
        ResponseApi<DownloadFileImportMaillingFileDTO> response = new ResponseApi<>();
        try {
            Optional<ImportMaillingFile> importMaillingFile = this.importMaillingFileService.findById(id);
                if(!importMaillingFile.isPresent()){
                    throw new ValidationException("Não encontrado import com codigo informado");
                }

            response.setData(new DownloadFileImportMaillingFileDTO(importMaillingFile.get().getFile()));
            LOGGER.debug("Cancelamento de import de arquivo realizado com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar c ancelamento de import de arquivo");
            List<String> erros = Arrays.asList("Erro inesperado ao realizar c ancelamento de import de arquivo");
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}

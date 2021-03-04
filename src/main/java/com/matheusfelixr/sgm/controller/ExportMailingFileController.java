package com.matheusfelixr.sgm.controller;

import com.matheusfelixr.sgm.model.domain.UserAuthentication;
import com.matheusfelixr.sgm.model.dto.MessageDTO;
import com.matheusfelixr.sgm.model.dto.config.ResponseApi;
import com.matheusfelixr.sgm.model.dto.exportMailingFile.GenerateFileRequestDTO;
import com.matheusfelixr.sgm.service.ExportMailingFileService;
import com.matheusfelixr.sgm.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/export-mailing-file")
public class ExportMailingFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportMailingFileController.class);

    @Autowired
    private ExportMailingFileService exportMailingFileService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value  = "/generate-file")
    public ResponseEntity<ResponseApi<MessageDTO>> generateFile(@RequestBody GenerateFileRequestDTO generateFileRequestDTO) {
        LOGGER.debug("Inicio processo para gerar arquivo de export mailing " + generateFileRequestDTO.toString());
        ResponseApi<MessageDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();

            response.setData(exportMailingFileService.generateFile(GenerateFileRequestDTO.convertToEntity(generateFileRequestDTO),currentUser));
            LOGGER.debug("Sucesso processo para gerar arquivo de export mailing ");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> errors = Arrays.asList(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao exportar arquivo de mailings realizados");
            List<String> errors = Arrays.asList("Erro inesperado ao exportar arquivo. Procure o suporte.");
            response.setErrors(errors);
            return ResponseEntity.ok(response);
        }
    }
}

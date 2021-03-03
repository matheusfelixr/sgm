package com.matheusfelixr.sgm.controller;

import com.matheusfelixr.sgm.model.domain.UserAuthentication;
import com.matheusfelixr.sgm.model.dto.config.ResponseApi;
import com.matheusfelixr.sgm.model.dto.mailingType.MailingTypeDTO;
import com.matheusfelixr.sgm.service.GenerateClassService;
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
@RequestMapping(path = "/generate-class")
public class GenerateClassController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateClassController.class);

    @Autowired
    private GenerateClassService generateClassService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value  = "/generate-class")
    public ResponseEntity<ResponseApi<String>> generateClass() {
        LOGGER.debug("Inicio processo de importar formato de arquivo de arquivo.");
        ResponseApi<String> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();
            this.generateClassService.generateClass();
            response.setData("oapoap");
            LOGGER.debug("Importacao realizada com sucesso.");
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
}

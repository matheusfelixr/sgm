package com.wise.sgm.controller;

import com.wise.sgm.model.domain.Mailing;
import com.wise.sgm.model.dto.config.ResponseApi;
import com.wise.sgm.model.dto.mailingType.MailingTypeDTO;
import com.wise.sgm.service.MailingService;
import com.wise.sgm.service.MailingTypeService;
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

@RestController
@CrossOrigin
@RequestMapping(path = "/mailing")
public class MailingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailingController.class);

    @Autowired
    private MailingService mailingService;

    @GetMapping(value  = "/get-next-mailing")
    public ResponseEntity<ResponseApi<Mailing>> getNextMailing() {
        LOGGER.debug("Inicio processo para pegar novo mailing");
        ResponseApi<Mailing> response = new ResponseApi<>();
        try {
            Mailing nextMailing = mailingService.getNextMailing();
            response.setData( new Mailing());
            LOGGER.debug("processo para pegar novo mailing realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar pegar novo mailing");
            List<String> erros = Arrays.asList("Erro inesperado ao realizar pegar novo mailing");
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}

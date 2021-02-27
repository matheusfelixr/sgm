package com.wise.sgm.controller;

import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.dto.config.ResponseApi;
import com.wise.sgm.model.dto.mailling.NextMaillingDTO;
import com.wise.sgm.service.MaillingService;
import com.wise.sgm.service.SecurityService;
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
@RequestMapping(path = "/mailling")
public class MaillingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaillingController.class);

    @Autowired
    private MaillingService maillingService;

    @Autowired
    private SecurityService securityService;

    @GetMapping(value  = "/get-next-mailling")
    public ResponseEntity<ResponseApi<NextMaillingDTO>> getNextMailling() {
        LOGGER.debug("Inicio processo para pegar novo mailling");
        ResponseApi<NextMaillingDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();

            response.setData(NextMaillingDTO.convertToDTO(maillingService.getNextMailling(currentUser)));
            LOGGER.debug("processo para pegar novo mailling realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar novo mailling");
            List<String> erros = Arrays.asList("Erro inesperado ao realizar novo mailling");
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        }
    }
}

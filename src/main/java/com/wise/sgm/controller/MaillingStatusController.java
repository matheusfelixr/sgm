package com.wise.sgm.controller;

import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.dto.config.ResponseApi;
import com.wise.sgm.model.dto.mailling.NextMaillingDTO;
import com.wise.sgm.model.dto.maillingStatus.CreateMaillingStatusDTO;
import com.wise.sgm.model.dto.maillingStatus.MaillingStatusDTO;
import com.wise.sgm.model.dto.maillingStatus.ReturnReansonMaillingDTO;
import com.wise.sgm.model.enums.ReasonMaillingEnum;
import com.wise.sgm.service.MaillingStatusService;
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
@RequestMapping(path = "/mailling-status")
public class MaillingStatusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaillingStatusController.class);

    @Autowired
    private MaillingStatusService maillingStatusService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value  = "/create")
    public ResponseEntity<ResponseApi<MaillingStatusDTO>> create(@RequestBody CreateMaillingStatusDTO createMaillingStatusDTO) {
        LOGGER.debug("Inicio processo para criação de mailling status");
        ResponseApi<MaillingStatusDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();

            response.setData(MaillingStatusDTO.convertToDTO(maillingStatusService.create(CreateMaillingStatusDTO.convertToEntity(createMaillingStatusDTO), currentUser)));
            LOGGER.debug("Processo para criação de mailling status realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao criar novo mailling status");
            List<String> erros = Arrays.asList("Erro inesperado ao criar novo mailling status");
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping(value  = "/get-by-reason-mailling/{reason-mailling}")
    public ResponseEntity<ResponseApi<List<MaillingStatusDTO>>> getByReasonMailling(@PathVariable(value = "reason-mailling") String ReasonMailling) {
        LOGGER.debug("Inicio processo para buscar mailling status");
        ResponseApi<List<MaillingStatusDTO>> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();

            response.setData(MaillingStatusDTO.convertToListDTO(maillingStatusService.findByReasonMailling(ReasonMaillingEnum.findByDescription(ReasonMailling))));
            LOGGER.debug("Processo para buscar de mailling status realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao buscar mailling status");
            List<String> erros = Arrays.asList("Erro inesperado ao buscar mailling status");
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping(value  = "/find-all-reason-mailling")
    public ResponseEntity<ResponseApi<List<ReturnReansonMaillingDTO>>> findAllReasonMailling() {
        LOGGER.debug("Inicio processo para buscar Reason mailling");
        ResponseApi<List<ReturnReansonMaillingDTO>> response = new ResponseApi<>();
        try {
            response.setData(ReturnReansonMaillingDTO.convertToListDTO(Arrays.asList(ReasonMaillingEnum.values())));
            LOGGER.debug("Processo para buscar de Reason mailling realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao buscar reason mailling");
            List<String> erros = Arrays.asList("Erro inesperado ao buscar reason mailling");
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        }
    }
}

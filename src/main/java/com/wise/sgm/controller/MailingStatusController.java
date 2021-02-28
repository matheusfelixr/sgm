package com.wise.sgm.controller;

import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.dto.config.ResponseApi;
import com.wise.sgm.model.dto.mailingStatus.CreateMailingStatusDTO;
import com.wise.sgm.model.dto.mailingStatus.MailingStatusDTO;
import com.wise.sgm.model.dto.mailingStatus.ReturnReansonMailingDTO;
import com.wise.sgm.model.enums.ReasonMailingEnum;
import com.wise.sgm.service.MailingStatusService;
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
@RequestMapping(path = "/mailing-status")
public class MailingStatusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailingStatusController.class);

    @Autowired
    private MailingStatusService mailingStatusService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value  = "/create")
    public ResponseEntity<ResponseApi<MailingStatusDTO>> create(@RequestBody CreateMailingStatusDTO createMailingStatusDTO) {
        LOGGER.debug("Inicio processo para criação de mailing status");
        ResponseApi<MailingStatusDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();

            response.setData(MailingStatusDTO.convertToDTO(mailingStatusService.create(CreateMailingStatusDTO.convertToEntity(createMailingStatusDTO), currentUser)));
            LOGGER.debug("Processo para criação de mailing status realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao criar novo mailing status");
            List<String> erros = Arrays.asList("Erro inesperado ao criar novo mailing status");
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping(value  = "/get-by-reason-mailing/{reason-mailing}")
    public ResponseEntity<ResponseApi<List<MailingStatusDTO>>> getByReasonMailing(@PathVariable(value = "reason-mailing") String ReasonMailing) {
        LOGGER.debug("Inicio processo para buscar  status");
        ResponseApi<List<MailingStatusDTO>> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();

            response.setData(MailingStatusDTO.convertToListDTO(mailingStatusService.findByReasonMailing(ReasonMailingEnum.findByDescription(ReasonMailing))));
            LOGGER.debug("Processo para buscar de mailing status realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao buscar mailing status");
            List<String> erros = Arrays.asList("Erro inesperado ao buscar mailing status");
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping(value  = "/find-all-reason-mailing")
    public ResponseEntity<ResponseApi<List<ReturnReansonMailingDTO>>> findAllReasonMailing() {
        LOGGER.debug("Inicio processo para buscar Reason mailing");
        ResponseApi<List<ReturnReansonMailingDTO>> response = new ResponseApi<>();
        try {
            response.setData(ReturnReansonMailingDTO.convertToListDTO(Arrays.asList(ReasonMailingEnum.values())));
            LOGGER.debug("Processo para buscar de Reason mailing realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao buscar reason mailing");
            List<String> erros = Arrays.asList("Erro inesperado ao buscar reason mailing");
            response.setErrors(erros);
            return ResponseEntity.ok(response);
        }
    }
}

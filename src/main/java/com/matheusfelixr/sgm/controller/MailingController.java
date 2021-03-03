package com.matheusfelixr.sgm.controller;

import com.matheusfelixr.sgm.model.domain.UserAuthentication;
import com.matheusfelixr.sgm.model.dto.MessageDTO;
import com.matheusfelixr.sgm.model.dto.config.ResponseApi;
import com.matheusfelixr.sgm.model.dto.mailing.NextMailingDTO;
import com.matheusfelixr.sgm.model.dto.mailing.SaveAttendanceDTO;
import com.matheusfelixr.sgm.service.MailingService;
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
@RequestMapping(path = "/mailing")
public class MailingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailingController.class);

    @Autowired
    private MailingService mailingService;

    @Autowired
    private SecurityService securityService;

    @GetMapping(value  = "/get-next-mailing")
    public ResponseEntity<ResponseApi<NextMailingDTO>> getNextMailing() {
        LOGGER.debug("Inicio processo para pegar novo mailing");
        ResponseApi<NextMailingDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();

            response.setData(NextMailingDTO.convertToDTO(mailingService.getNextMailing(currentUser)));
            LOGGER.debug("processo para pegar novo mailing realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> errors = Arrays.asList(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar novo mailing");
            List<String> errors = Arrays.asList("Erro inesperado ao realizar novo mailing");
            response.setErrors(errors);
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping(value  = "/save-attendance")
    public ResponseEntity<ResponseApi<MessageDTO>> saveAttendance(@RequestBody SaveAttendanceDTO saveAttendanceDTO) {
        LOGGER.info("Inicio processo para salvar atendimento com ids: " +saveAttendanceDTO.toString());
        ResponseApi<MessageDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();

            response.setData(mailingService.saveAttendance(saveAttendanceDTO.getIdMailingStatus(), saveAttendanceDTO.getIdMailing(),currentUser));
            LOGGER.info("Sucesso ao salvar atendimento");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> errors = Arrays.asList(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao salvar atendimento");
            List<String> errors = Arrays.asList("Erro inesperado ao salvar atendimento");
            response.setErrors(errors);
            return ResponseEntity.ok(response);
        }
    }
}

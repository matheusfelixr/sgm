package com.wise.sgm.controller;

import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.dto.config.ResponseApi;
import com.wise.sgm.model.dto.mailingType.MailingTypeDTO;
import com.wise.sgm.service.MailingTypeService;
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

@RestController
@CrossOrigin
@RequestMapping(path = "/mailing-type")
public class MailingTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailingTypeController.class);

    @Autowired
    private MailingTypeService mailingTypeService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value  = "/save-format-layout")
    public ResponseEntity<ResponseApi<MailingTypeDTO>> saveFormatLayout(@RequestParam("file") MultipartFile file) {
        LOGGER.debug("Inicio processo de importar formato de arquivo de arquivo.");
        ResponseApi<MailingTypeDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();

            response.setData(MailingTypeDTO.convertToDTO(this.mailingTypeService.saveFormatLayout(file, currentUser)));
            LOGGER.debug("Importacao realizada com sucesso.");
            return ResponseEntity.ok(response);
        }catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao realizar importacao");
            List<String> erros = Arrays.asList("Erro inesperado ao realizar importação");
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}

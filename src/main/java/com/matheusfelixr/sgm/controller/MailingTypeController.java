package com.matheusfelixr.sgm.controller;

import com.matheusfelixr.sgm.model.domain.UserAuthentication;
import com.matheusfelixr.sgm.service.SecurityService;
import com.matheusfelixr.sgm.model.dto.config.ResponseApi;
import com.matheusfelixr.sgm.model.dto.mailingType.MailingTypeDTO;
import com.matheusfelixr.sgm.service.MailingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

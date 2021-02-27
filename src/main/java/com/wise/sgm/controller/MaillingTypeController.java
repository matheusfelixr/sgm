package com.wise.sgm.controller;

import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.dto.config.ResponseApi;
import com.wise.sgm.model.dto.maillingType.MaillingTypeDTO;
import com.wise.sgm.service.MaillingTypeService;
import com.wise.sgm.service.SecurityService;
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
@RequestMapping(path = "/mailling-type")
public class MaillingTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaillingTypeController.class);

    @Autowired
    private MaillingTypeService maillingTypeService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value  = "/save-format-layout")
    public ResponseEntity<ResponseApi<MaillingTypeDTO>> saveFormatLayout(@RequestParam("file") MultipartFile file) {
        LOGGER.debug("Inicio processo de importar formato de arquivo de arquivo.");
        ResponseApi<MaillingTypeDTO> response = new ResponseApi<>();
        try {
            UserAuthentication currentUser = securityService.getCurrentUser();

            response.setData(MaillingTypeDTO.convertToDTO(this.maillingTypeService.saveFormatLayout(file, currentUser)));
            LOGGER.debug("Importacao realizada com sucesso.");
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
}

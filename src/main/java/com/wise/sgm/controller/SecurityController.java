package com.wise.sgm.controller;

import com.wise.sgm.model.dto.config.ResponseApi;
import com.wise.sgm.model.dto.security.*;
import com.wise.sgm.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/security")
public class SecurityController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

	@Autowired
	private SecurityService securityService;

	@PostMapping(value  = "/authenticate")
	public ResponseEntity<ResponseApi<AuthenticateResponseDTO>> authenticate(@RequestBody AuthenticateRequestDTO authenticateRequestDTO, HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.debug("Inicio processo de autenticacao.");
		ResponseApi<AuthenticateResponseDTO> response = new ResponseApi<>();
		try {
			response.setData(this.securityService.authenticate(authenticateRequestDTO,httpServletRequest ));
			LOGGER.debug("Autenticacao realizada com sucesso.");
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao tentar autenticar");
			List<String> erros = Arrays.asList("Erro inesperado ao tentar autenticar");
			response.setErrors(erros);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	@PostMapping(value  = "/reset-password")
	public ResponseEntity<ResponseApi<ResetPasswordResponseDTO>> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO, HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.debug("Inicio processo de reset de senha.");
		ResponseApi<ResetPasswordResponseDTO> response = new ResponseApi<>();
		try {
			response.setData(this.securityService.resetPassword(resetPasswordRequestDTO.getUsername(),httpServletRequest));
			LOGGER.debug("Reset de senha realizado com sucesso.");
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao tentar resetar senha");
			List<String> erros = Arrays.asList("Erro inesperado ao tentar resetar senha");
			response.setErrors(erros);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}


	@PostMapping(value  = "/create-user")
	public ResponseEntity<ResponseApi<CreateUserResponseDTO>> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) throws Exception {
		LOGGER.debug("Inicio processo de criação de usuario.");
		ResponseApi<CreateUserResponseDTO> response = new ResponseApi<>();
		try {
			response.setData(this.securityService.createUser(createUserRequestDTO));
			LOGGER.debug("Processo de criação de usuario realizado com sucesso.");
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao tentar criar novo usuário");
			List<String> erros = Arrays.asList("Erro inesperado ao tentar criar novo usuário");
			response.setErrors(erros);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}



}
package com.wise.sgm.model.dto.security;

import lombok.Data;

@Data
public class AuthenticateResponseDTO {
	
	private String userName;
	private String token;
	private Boolean changePassword;

	public AuthenticateResponseDTO(String userName, String token, Boolean changePassword) {
		this.userName = userName;
		this.token = token;
		this.changePassword = changePassword;
	}

	public AuthenticateResponseDTO() {
	}
}

package com.wise.sgm.model.dto.security;

import lombok.Data;

@Data
public class NewPasswordResponseDTO {

	private String message;

	public NewPasswordResponseDTO(String message) {
		this.message = message;
	}
}

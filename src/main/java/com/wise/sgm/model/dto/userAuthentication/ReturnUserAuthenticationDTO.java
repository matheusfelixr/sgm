package com.wise.sgm.model.dto.userAuthentication;

import lombok.Data;

@Data
public class ReturnUserAuthenticationDTO {

    private String userName;

    public ReturnUserAuthenticationDTO(String userName) {
        this.userName = userName;
    }
}

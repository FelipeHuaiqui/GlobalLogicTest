package com.globallogic.globallogic.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class SignUpResponse {
    private UUID id;
    private String created;
    private String lastLogin;
    private String token;
    private Boolean isActive;
}

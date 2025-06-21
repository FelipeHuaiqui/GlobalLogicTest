package com.globallogic.globallogic.dto.response;

import com.globallogic.globallogic.dto.request.PhonesTO;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String id;
    private String created;
    private String lastLogin;
    private String token;
    private Boolean isActive;
    private String name;
    private String email;
    private String password;
    private List<PhonesTO> phones;
}

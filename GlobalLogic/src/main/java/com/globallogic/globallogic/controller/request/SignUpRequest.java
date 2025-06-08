package com.globallogic.globallogic.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class SignUpRequest {

    private String name;

    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
            message = "Correo con formato inválido")
    @NotNull
    @NotEmpty
    private String email;

    @Pattern(
            regexp = "^(?=(?:[^A-Z]*[A-Z]){1}[^A-Z]*$)(?=(?:[^0-9]*[0-9]){2}[^0-9]*$)[a-zA-Z0-9]{8,12}$",
            message = "Debe tener 1 mayúscula, 2 números, minúsculas y entre 8 y 12 caracteres.")
    @NotNull
    @NotEmpty
    private String password;

    private List<PhonesTO> phones;
}

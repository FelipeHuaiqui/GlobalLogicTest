package com.globallogic.globallogic.repository;


import lombok.Data;
import javax.persistence.*;

import java.util.List;
import java.util.UUID;

@Table(name = "Users")
@Entity
@Data
public class UserRegister {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String created;
    private String lastLogin;
    private String token;
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Phone> phones;
}

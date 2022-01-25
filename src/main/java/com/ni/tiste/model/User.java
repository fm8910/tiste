package com.ni.tiste.model;

import com.ni.tiste.handleerrors.UniqueEmail;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint( columnNames = "email")
        })
@Data
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    private String token;

    @NotNull
    private LocalDateTime created;

    private LocalDateTime modified;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    private Boolean isActive;

    @OneToMany(mappedBy = "user")
    private Set<Phone> phones;

}

package com.enzo.entities;

import com.enzo.abstracts.TimeStamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="User")
@Table(name="users")

@JsonIgnoreProperties(allowGetters= false, ignoreUnknown = true)

public class UserEntity extends TimeStamp    {

    @Column(name="lock")
    @JsonProperty(value = "isLock", required = false )
    private Boolean isLock = true;

    @Column(name="name")
    @JsonProperty(value = "name", required = true )
    @NotNull
    private String name;

    @Column(name="email", unique=true)
    @JsonProperty(value = "email", required = true )
    @NotNull
    private String email;

    @Column(name="phone")
    @JsonProperty(value = "phone", required = false )
    private String phone;

    @Column(name="mdp")
    @JsonProperty(value = "password", required = false )
    private String password;

    @Column(name="cfp")
    @JsonProperty(value = "confirm", required = false )
    private String cfp;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="role_id")
    @JsonProperty(value = "role", required = false )
    private Role role;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    @JsonBackReference
    @JsonProperty(value = "compte", required = false )
    private Compte compte;

    @ManyToMany(cascade=CascadeType.ALL)//(fetch=FetchType.EAGER)
    @JoinTable(name="access_permissions",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="permission_id")})
    @JsonProperty(value = "permissions", required = false )
    private List<Permission> permissions = new ArrayList<>();


}

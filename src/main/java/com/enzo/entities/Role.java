package com.enzo.entities;

import com.enzo.abstracts.TimeStamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Role")
@Table(name="roles")
@JsonIgnoreProperties(ignoreUnknown = true)

public class Role extends TimeStamp {

    @Column(name="code", unique = true)
    @JsonProperty(value = "code", required = true )
    private String code;

    @Column(name="description")
    @JsonProperty(value = "description", required = false )
    private String description;

    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(name="roles_permissions",
            joinColumns={@JoinColumn(name="role_id")},
            inverseJoinColumns={@JoinColumn(name="permission_id")})
    @JsonProperty(value = "permissions", required = false )
    private List<Permission> permissions = new ArrayList<>();

}

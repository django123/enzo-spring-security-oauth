package com.enzo.entities;


import com.enzo.abstracts.TimeStamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Compte")
@Table(name="comptes")
@JsonIgnoreProperties(ignoreUnknown = true)

public class Compte extends TimeStamp {

    @JsonProperty(value = "socialReason", required = true )
    @NotNull
    private String socialReason;

    @Column(name="country")
    @JsonProperty(value = "country", required = false )
    private String country;

    @Column(name="zip_code")
    @JsonProperty(value = "zip_code", required = false )
    private String zipCode;

    @Column(name="town")
    @JsonProperty(value = "town", required = false )
    private String town;

    @Column(name="address")
    @JsonProperty(value = "address", required = false )
    private String address;


    @OneToMany(mappedBy = "compte")
    @JsonManagedReference
    List<UserEntity> users = new ArrayList<>();

}

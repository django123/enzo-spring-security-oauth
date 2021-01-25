package com.enzo.entities;

import com.enzo.abstracts.TimeStamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Permission")
@Table(name="permissions")
@JsonIgnoreProperties(ignoreUnknown = true)

public class Permission extends TimeStamp {

    @Column(name="code", unique = true)
    @JsonProperty(value = "code", required = true )
    private String code;

    @Column(name="description")
    @JsonProperty(value = "description", required = false )
    private String description;

}

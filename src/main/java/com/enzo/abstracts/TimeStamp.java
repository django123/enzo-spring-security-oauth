package com.enzo.abstracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class TimeStamp implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id", unique =true)
    @JsonProperty(value = "reference", required = false )
    private String id;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIME)
    private Date createdOn;


    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIME)
    private Date updatedOn;

    @Column(name = "deleted")
    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID().toString();
        createdAt = new Date();
        createdOn = new Date();
        updatedAt = new Date();
        updatedOn = new Date();
        isDeleted = false;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
        updatedOn = new Date();
    }

}

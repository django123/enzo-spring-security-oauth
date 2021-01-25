package com.enzo.abstracts;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters= false, ignoreUnknown = true)

public abstract class UserAbstract extends TimeStamp{
}

package com.df.citrusbytes.smartac.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;
import java.util.Date;

@Entity
public class AC {
    @Id
    public Long id;
    @Index
    public String serialNo;
    public Date registerationDate;
    public String firmwareVersion;
}

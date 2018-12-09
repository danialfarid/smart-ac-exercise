package com.df.citrusbytes.smartac.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity()
public class SensorReading {
    @Id
    public Long id;
    @Index
    public String serialNo;
    public float temperature;
    public float humidity;
    public float carbonMonoxide;
    public String healthStatus;
    @Index
    public Date date;
}

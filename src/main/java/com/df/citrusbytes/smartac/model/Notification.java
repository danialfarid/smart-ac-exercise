package com.df.citrusbytes.smartac.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity
public class Notification {
    @Id
    public Long id;
    @Index
    public boolean resolved;
    public SensorReading sensorReading;
    @Index
    public Date date;
}

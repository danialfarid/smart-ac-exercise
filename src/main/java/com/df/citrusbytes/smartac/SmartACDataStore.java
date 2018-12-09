package com.df.citrusbytes.smartac;

import com.df.citrusbytes.smartac.model.AC;
import com.df.citrusbytes.smartac.model.Notification;
import com.df.citrusbytes.smartac.model.SensorReading;
import com.googlecode.objectify.cmd.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Repository
public class SmartACDataStore {
    public void save(AC ac) {
        if (ofy().load().type(AC.class).filter("serialNo", ac.serialNo).count() > 0) {
            throw new IllegalArgumentException("Duplicate SerialNo");
        }
        ofy().save().entity(ac).now();
    }

    public void save(SensorReading sensorReading) {
        sensorReading.date = new Date();
        ofy().save().entity(sensorReading).now();
    }

    public List<AC> listACs(String serialNo) {
        Query<AC> query = ofy().load().type(AC.class).order("serialNo");
        if (serialNo != null) {
            query = query.filter("serialNo", serialNo);
        }
        return query.list();
    }

    public List<SensorReading> listSensorReadings(String serialNo, Date endDate) {
        return ofy().load().type(SensorReading.class)
                .filter("serialNo", serialNo)
                .filter("date >", endDate)
                .order("date").list();
    }

    public void save(Notification notification) {
        notification.date = new Date();
        ofy().save().entity(notification).now();
    }

    public List<Notification> listNotifications() {
        return ofy().load().type(Notification.class).filter("resolved", false)
                .order("date").list();
    }

    public void resolve(long id) {
        Notification notification = ofy().load().type(Notification.class).id(id).now();
        if (notification != null) {
            notification.resolved = true;
            ofy().save().entity(notification).now();
        }
    }
}

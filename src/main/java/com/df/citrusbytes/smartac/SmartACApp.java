package com.df.citrusbytes.smartac;

import com.df.citrusbytes.smartac.model.AC;
import com.df.citrusbytes.smartac.model.Notification;
import com.df.citrusbytes.smartac.model.SensorReading;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@RestController
public class SmartACApp {
    private SmartACDataStore dataStore;

    public SmartACApp(SmartACDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @GetMapping("/ac")
    public List<AC> listACs(@RequestParam(required = false) String serialNo) {
        return dataStore.listACs(serialNo);
    }

    @PostMapping("/ac")
    public void register(@RequestBody AC ac) {
        dataStore.save(ac);
    }

    @GetMapping("/ac/{serialNo}/sensorReading")
    public List<SensorReading> sensorReading(@PathVariable String serialNo, @RequestParam long endDate) {
        return dataStore.listSensorReadings(serialNo, new Date(endDate));
    }

    @PostMapping("/ac/{serialNo}/sensorReading")
    public void sensorReading(@PathVariable String serialNo, @RequestBody SensorReading sensorReading) {
        assert sensorReading.serialNo == serialNo;
        createNotifications(sensorReading);
        dataStore.save(sensorReading);
    }

    @GetMapping("/notifications")
    public List<Notification> listNotifications() {
        return dataStore.listNotifications();
    }

    @PostMapping("/notifications/{id}/resolve")
    public void resolveNotification(@PathVariable long id) {
        dataStore.resolve(id);
    }

    private void createNotifications(SensorReading sensorReading) {
        if (sensorReading.carbonMonoxide > 9) {
            Notification notification = new Notification();
            notification.sensorReading = sensorReading;
            dataStore.save(notification);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartACApp.class, args);
    }
}
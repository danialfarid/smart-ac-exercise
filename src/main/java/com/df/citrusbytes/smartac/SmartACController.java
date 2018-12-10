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
public class SmartACController {
    private SmartACDataStore dataStore;

    public SmartACController(SmartACDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @GetMapping("/ac")
    public List<AC> listACs(@RequestParam(required = false) String serialNo) {
        return dataStore.listACs(serialNo);
    }

    @PostMapping("/ac")
    public void register(@RequestBody AC ac) {
        ac.registerationDate = new Date();
        dataStore.save(ac);
    }

    @GetMapping("/ac/{serialNo}/sensorReading")
    public List<SensorReading> sensorReading(@PathVariable String serialNo, @RequestParam long endDate) {
        return dataStore.listSensorReadings(serialNo, new Date(endDate));
    }

    @PostMapping("/ac/{serialNo}/sensorReading")
    public void sensorReading(@PathVariable String serialNo, @RequestBody SensorReading sensorReading) {
        assert sensorReading.serialNo == serialNo;
        sensorReading.date = new Date();
//      sensorReading.date = randomizeDate(sensorReading);

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
        String status = sensorReading.healthStatus;
        if (sensorReading.carbonMonoxide > 9 ||
                "needs_service".equals(status) || "needs_new_filter".equals(status) ||
                "gas_leak".equals(status)) {
            Notification notification = new Notification();
            notification.sensorReading = sensorReading;
            dataStore.save(notification);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartACController.class, args);
    }

//    private Date randomizeDate(SensorReading sensorReading) {
//        Date d2 = new Date();
//        Date d1 = Date.from(ZonedDateTime.now().minusMonths(2).toInstant());
//        return new Date(ThreadLocalRandom.current()
//                .nextLong(d1.getTime(), d2.getTime()));
//    }
}
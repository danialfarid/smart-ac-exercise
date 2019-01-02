package com.df.citrusbytes.smartac;

import com.df.citrusbytes.smartac.model.AC;
import com.df.citrusbytes.smartac.model.Notification;
import com.df.citrusbytes.smartac.model.SensorReading;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@RestController
@Api(value = "smartAcAPI", description = "Operations pertaining to Smart AC including device registration, " +
        "reporting and storing sensor readings, and device search.")
public class SmartACController {
    private SmartACDataStore dataStore;

    public SmartACController(SmartACDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @ApiOperation("Get list of available AC devices. You can optionally filter by serial number.")
    @GetMapping(value = "/ac", produces = "application/json")
    public List<AC> listACs(@RequestParam(required = false) String serialNo) {
        return dataStore.listACs(serialNo);
    }

    @ApiOperation("Register a new AC device. Serial number must be unique.")
    @PostMapping(value = "/ac", consumes = "application/json")
    public void register(@RequestBody AC ac) {
        ac.registerationDate = new Date();
        dataStore.save(ac);
    }

    @ApiOperation("Get the list of sensor reading for a given device. The device is identified by serial number. " +
            "`endDate` request parameter is required and is in unix epoch millisecond format and indicates " +
            "the beginning of the time the logs will be retrieved from until now.")
    @GetMapping(value = "/ac/{serialNo}/sensorReading", produces = "application/json")
    public List<SensorReading> sensorReading(@PathVariable String serialNo, @RequestParam long endDate) {
        return dataStore.listSensorReadings(serialNo, new Date(endDate));
    }

    @ApiOperation(value = "Store the sensor readings values. The sensor readings are send as an array of size " +
            "at least one or more.", consumes = "application/json")
    @PostMapping(value = "/ac/{serialNo}/sensorReading", produces = "application/json")
    public void sensorReading(@PathVariable String serialNo, @RequestBody SensorReading[] sensorReadings) {
        assert sensorReadings.length > 0;
        for (SensorReading reading : sensorReadings) {
            saveSensorReading(reading);
        }
    }

    private void saveSensorReading(SensorReading sensorReading) {
        sensorReading.date = new Date();
//      sensorReading.date = randomizeDate(sensorReading);

        createNotifications(sensorReading);
        dataStore.save(sensorReading);
    }

    @ApiOperation("Get the list of active (unresolved) system notification. This include the " +
            "sensor readings with the carbon monoxide of above 9ppm or health status of " +
            "`needs_service`, `needs_new_filter` or `gas_leak`.")
    @GetMapping(value = "/notifications", produces = "application/json")
    public List<Notification> listNotifications() {
        return dataStore.listNotifications();
    }

    @ApiOperation("Mark a notification with the given ID as resolved.")
    @PostMapping(value = "/notifications/{id}/resolve")
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

    @ApiOperation(hidden = true, value = "redirect to homepage")
    @GetMapping({"/", "/callback"})
    public void homepage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("index.html").forward(request, response);
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartACController.class, args);
    }

//    private Date randomizeDate(SensorReading sensorReading) {
//        Date d2 = new Date();
//        Date d1 = Date.from(ZonedDateTime.now().minusDays(2).toInstant());
//        return new Date(ThreadLocalRandom.current()
//                .nextLong(d1.getTime(), d2.getTime()));
//    }
}
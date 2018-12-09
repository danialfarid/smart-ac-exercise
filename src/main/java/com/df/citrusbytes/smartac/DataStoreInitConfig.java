package com.df.citrusbytes.smartac;

import com.df.citrusbytes.smartac.model.AC;
import com.df.citrusbytes.smartac.model.Notification;
import com.df.citrusbytes.smartac.model.SensorReading;
import com.googlecode.objectify.ObjectifyFilter;
import com.googlecode.objectify.ObjectifyService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Configuration
public class DataStoreInitConfig implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        ObjectifyService.init();
        ObjectifyService.register(AC.class);
        ObjectifyService.register(SensorReading.class);
        ObjectifyService.register(Notification.class);
    }

    @Bean
    public FilterRegistrationBean<ObjectifyFilter> objectifyFilterRegistration() {
        final FilterRegistrationBean<ObjectifyFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ObjectifyFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}

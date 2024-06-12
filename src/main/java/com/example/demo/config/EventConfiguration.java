package com.example.demo.config;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * 描述
 *
 * @author 王俊
 */
@Configuration
public class EventConfiguration {




    @EventListener
    void handleEvent(ApplicationEvent event) {
        System.out.println("handleEvent: " + event);
    }
}

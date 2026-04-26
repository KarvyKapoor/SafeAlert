package com.emergency.response_system.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.emergency.response_system.model.Location;

@Controller
public class TrackingController {

    @MessageMapping("/location")
    @SendTo("/topic/location")
    public Location sendLocation(Location location) {
        System.out.println("Live Location: " 
            + location.getLatitude() + ", " 
            + location.getLongitude());

        return location;
    }
}

package com.emergency.response_system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emergency.response_system.model.EmergencyContact;
import com.emergency.response_system.model.Location;

@Service
public class EmergencyService {

    private final List<EmergencyContact> contacts = new ArrayList<>();

    // Add contact
    public void addContact(EmergencyContact contact) {
        contacts.add(contact);
        System.out.println("Contact added: " + contact.getName());
    }

    // Get all contacts
    public List<EmergencyContact> getContacts() {
        return contacts;
    }

    // Trigger alert
    public void triggerAlert(Location location) {

    String mapLink = "https://maps.google.com/?q="
            + location.getLatitude() + ","
            + location.getLongitude();

    String msg = "EMERGENCY ALERT!\n"
            + "Location: " + mapLink;

    System.out.println(msg);

    for (EmergencyContact contact : contacts) {

        System.out.println("Sending email to: " + contact.getEmail());

        emailService.sendEmail(contact.getEmail(), msg);
    }
}
    @Autowired
    private EmailService emailService;
}

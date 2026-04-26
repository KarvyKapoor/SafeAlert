package com.emergency.response_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.emergency.response_system.model.*;
import com.emergency.response_system.repository.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private EmergencyContactRepository contactRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/{userId}")
    public String addContact(@PathVariable Long userId,
                             @RequestBody EmergencyContact contact) {

        User user = userRepo.findById(userId).orElse(null);

        contact.setUser(user);
        contactRepo.save(contact);

        return "Contact added";
    }

    @GetMapping("/{userId}")
    public List<EmergencyContact> getContacts(@PathVariable Long userId) {

        List<EmergencyContact> contacts = contactRepo.findByUserId(userId);

        System.out.println("CONTACTS SIZE: " + contacts.size());

        return contacts;
    }
}
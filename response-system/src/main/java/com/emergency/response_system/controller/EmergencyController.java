package com.emergency.response_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emergency.response_system.model.EmergencyContact;
import com.emergency.response_system.model.Location;
import com.emergency.response_system.model.PanicAlertRecord;
import com.emergency.response_system.model.User;
import com.emergency.response_system.repository.EmergencyContactRepository;
import com.emergency.response_system.repository.LocationRepository;
import com.emergency.response_system.repository.PanicAlertRecordRepository;
import com.emergency.response_system.repository.UserRepository;
import com.emergency.response_system.service.EmailService;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyController {

    @Autowired
    private EmergencyContactRepository contactRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PanicAlertRecordRepository alertRepo;

    @Autowired
    private LocationRepository locationRepo;

    @Autowired
    private EmailService emailService;

    @PostMapping("/trigger/{userId}")
    @Transactional
    public String trigger(@PathVariable long userId,
                          @RequestBody Location location) {

        System.out.println("🔥 EMERGENCY TRIGGER HIT");
        System.out.println("USER ID: " + userId);

        var userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) {
            System.out.println("⚠️ User not found: " + userId);
            return "User not found";
        }

        User user = userOpt.get();

        Location savedLocation = new Location();
        savedLocation.setUser(user);
        savedLocation.setLatitude(location.getLatitude());
        savedLocation.setLongitude(location.getLongitude());
        savedLocation.setTriggeredAt(java.time.LocalDateTime.now());
        savedLocation.setPanicTriggered(true);
        Location persistedLocation = locationRepo.save(savedLocation);
        System.out.println("✅ Panic location persisted in location table: id=" + persistedLocation.getId()
                + " lat=" + persistedLocation.getLatitude() + " lng=" + persistedLocation.getLongitude());

        PanicAlertRecord record = new PanicAlertRecord();
        record.setUser(user);
        record.setLatitude(location.getLatitude());
        record.setLongitude(location.getLongitude());
        record.setTriggeredAt(java.time.LocalDateTime.now());
        alertRepo.save(record);

        List<EmergencyContact> contacts = contactRepo.findByUserId(userId);

        System.out.println("CONTACT SIZE: " + contacts.size());

        String msg = """
                🚨 EMERGENCY ALERT 🚨
                Latitude: %s
                Longitude: %s
                
                Track here: https://maps.google.com/?q=%s,%s
                """.formatted(
                        location.getLatitude(),
                        location.getLongitude(),
                        location.getLatitude(),
                        location.getLongitude()
                );

        for (EmergencyContact c : contacts) {
            if (c.getEmail() == null || c.getEmail().isBlank()) {
                System.out.println("⚠️ Skipping contact with no email: " + c.getName());
                continue;
            }

            System.out.println("➡ SENDING EMAIL TO: " + c.getEmail());
            emailService.sendEmail(c.getEmail(), msg);
        }

        return "Alert sent successfully - panic location id: " + persistedLocation.getId();
    }

    @GetMapping("/history/{userId}")
    public List<PanicAlertRecord> getAlertHistory(@PathVariable Long userId) {
        return alertRepo.findByUserIdOrderByTriggeredAtDesc(userId);
    }

    @GetMapping("/panics")
    public List<Location> getPanicLocations() {
        return locationRepo.findByPanicTriggeredTrue();
    }
}

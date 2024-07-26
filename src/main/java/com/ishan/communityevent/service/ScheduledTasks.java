package com.ishan.communityevent.service;

import com.ishan.communityevent.model.Event;
import com.ishan.communityevent.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduledTasks {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 0 8 * * *") // Every day at 8 AM
    public void sendEventReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayLater = now.plusDays(1);

        List<Event> upcomingEvents = eventRepository.findAllByStartTimeBetween(now, oneDayLater);

        for (Event event : upcomingEvents) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("user@example.com");
            message.setSubject("Reminder: Upcoming Event - " + event.getName());
            message.setText("Reminder for your upcoming event:\n" +
                    "Name: " + event.getName() + "\n" +
                    "Description: " + event.getDescription() + "\n" +
                    "Location: " + event.getLocation() + "\n" +
                    "Start Time: " + event.getStartTime() + "\n" +
                    "End Time: " + event.getEndTime());
            mailSender.send(message);
        }
    }
}

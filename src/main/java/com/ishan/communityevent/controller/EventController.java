package com.ishan.communityevent.controller;
import org.springframework.ui.Model;
import com.ishan.communityevent.model.Event;
import com.ishan.communityevent.model.RSVP;
import com.ishan.communityevent.model.Ticket;
import com.ishan.communityevent.service.EventService;
import com.ishan.communityevent.service.RSVPService;
import com.ishan.communityevent.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private RSVPService rsvpService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/")
    public String index(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "index";
    }

    @GetMapping("/events/new")
    public String createEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "create_event";
    }

    @PostMapping("/events")
    public String createEvent(@ModelAttribute Event event) {
        eventService.saveEvent(event);
        sendNotification(event);
        return "redirect:/";
    }

    @GetMapping("/events/{id}")
    public String viewEvent(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "event_detail";
    }

    @PostMapping("/events/{id}/rsvp")
    public String rsvpForEvent(@PathVariable Long id, @RequestParam String userEmail) {
        RSVP rsvp = new RSVP();
        rsvp.setEventId(id);
        rsvp.setUserEmail(userEmail);
        rsvpService.saveRSVP(rsvp);
        return "redirect:/events/" + id;
    }

    @PostMapping("/events/{id}/tickets")
    public String purchaseTicket(@PathVariable Long id, @RequestParam String userEmail, @RequestParam int quantity) {
        Ticket ticket = new Ticket();
        ticket.setEventId(id);
        ticket.setUserEmail(userEmail);
        ticket.setQuantity(quantity);
        ticketService.saveTicket(ticket);
        return "redirect:/events/" + id;
    }

    @GetMapping("/search")
    public String searchEvents(@RequestParam String keyword, Model model) {
        List<Event> events = eventService.searchEvents(keyword);
        model.addAttribute("events", events);
        return "index";
    }

    private void sendNotification(Event event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("admin@example.com");
        message.setSubject("New Event Created: " + event.getName());
        message.setText("A new event has been created. Details:\n" +
                "Name: " + event.getName() + "\n" +
                "Description: " + event.getDescription() + "\n" +
                "Location: " + event.getLocation() + "\n" +
                "Start Time: " + event.getStartTime() + "\n" +
                "End Time: " + event.getEndTime());
        mailSender.send(message);
    }
}

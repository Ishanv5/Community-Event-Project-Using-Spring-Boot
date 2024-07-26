package com.ishan.communityevent.service;

import com.ishan.communityevent.model.RSVP;
import com.ishan.communityevent.repository.RSVPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RSVPService {

    @Autowired
    private RSVPRepository rsvpRepository;

    public RSVP saveRSVP(RSVP rsvp) {
        return rsvpRepository.save(rsvp);
    }
}

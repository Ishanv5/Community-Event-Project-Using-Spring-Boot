package com.ishan.communityevent.repository;

import com.ishan.communityevent.model.RSVP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RSVPRepository extends JpaRepository<RSVP, Long> {
}

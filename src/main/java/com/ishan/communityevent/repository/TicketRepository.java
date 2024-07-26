package com.ishan.communityevent.repository;

import com.ishan.communityevent.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}

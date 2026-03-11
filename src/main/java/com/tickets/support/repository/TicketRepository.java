package com.tickets.support.repository;

import com.tickets.support.model.Ticket;
import com.tickets.support.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    List<Ticket> findByUser(User user);
    List<Ticket> findByUserOrderByCreatedAtDesc(User user);
    List<Ticket> findByUserId(Long userId);
}
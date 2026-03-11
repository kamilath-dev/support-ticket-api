package com.tickets.support.repository;

import com.tickets.support.model.Ticket;
import com.tickets.support.model.User;
import com.tickets.support.model.enums.TicketPriority;
import com.tickets.support.model.enums.TicketStatus;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public class TicketSpecifications {
    
    public static Specification<Ticket> belongsToUser(User user) {
        return (root, query, cb) -> cb.equal(root.get("user"), user);
    }
    
    public static Specification<Ticket> titleOrDescriptionContains(String keyword) {
        return (root, query, cb) -> cb.or(
            cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%"),
            cb.like(cb.lower(root.get("description")), "%" + keyword.toLowerCase() + "%")
        );
    }
    
    public static Specification<Ticket> hasStatus(TicketStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
    
    public static Specification<Ticket> hasPriority(TicketPriority priority) {
        return (root, query, cb) -> cb.equal(root.get("priority"), priority);
    }
}
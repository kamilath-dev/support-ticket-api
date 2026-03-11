package com.tickets.support.controller;

import com.tickets.support.dto.TicketResponse;
import com.tickets.support.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private TicketService ticketService;
    
    @GetMapping("/tickets")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<TicketResponse> getAllTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {
        
        return ticketService.getAllTickets(page, size, search, status, priority);
    }
    
    @PutMapping("/tickets/{id}/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public TicketResponse assignTicket(@PathVariable Long id, @RequestParam Long userId) {
        return ticketService.assignTicket(id, userId);
    }
}
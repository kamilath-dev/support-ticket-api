package com.tickets.support.service;

import com.tickets.support.dto.TicketRequest;
import com.tickets.support.dto.TicketResponse;
import com.tickets.support.model.Ticket;
import com.tickets.support.model.User;
import com.tickets.support.model.enums.TicketPriority;
import com.tickets.support.model.enums.TicketStatus;
import com.tickets.support.repository.TicketRepository;
import com.tickets.support.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public TicketResponse createTicket(TicketRequest ticketRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketRequest.getTitle());
        ticket.setDescription(ticketRequest.getDescription());
        
        if (ticketRequest.getPriority() != null) {
            ticket.setPriority(TicketPriority.valueOf(ticketRequest.getPriority()));
        } else {
            ticket.setPriority(TicketPriority.MEDIUM);
        }
        
        ticket.setUser(user);
        
        Ticket savedTicket = ticketRepository.save(ticket);
        return mapToResponse(savedTicket);
    }
    
    public List<TicketResponse> getUserTickets(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return ticketRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public TicketResponse getTicketById(Long id, String username) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        
        if (!ticket.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You don't have permission to view this ticket");
        }
        
        return mapToResponse(ticket);
    }
    
    public TicketResponse updateTicket(Long id, TicketRequest ticketRequest, String username) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        
        if (!ticket.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You don't have permission to update this ticket");
        }
        
        ticket.setTitle(ticketRequest.getTitle());
        ticket.setDescription(ticketRequest.getDescription());
        
        if (ticketRequest.getPriority() != null) {
            ticket.setPriority(TicketPriority.valueOf(ticketRequest.getPriority()));
        }
        
        Ticket updatedTicket = ticketRepository.save(ticket);
        return mapToResponse(updatedTicket);
    }
    
    public void deleteTicket(Long id, String username) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        
        if (!ticket.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You don't have permission to delete this ticket");
        }
        
        ticketRepository.delete(ticket);
    }
    
    private TicketResponse mapToResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setStatus(ticket.getStatus() != null ? ticket.getStatus().toString() : "OPEN");
        response.setPriority(ticket.getPriority() != null ? ticket.getPriority().toString() : "MEDIUM");
        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());
        response.setUserId(ticket.getUser().getId());
        response.setUsername(ticket.getUser().getUsername());
        return response;
    }
}
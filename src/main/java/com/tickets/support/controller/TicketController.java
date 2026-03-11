package com.tickets.support.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.support.dto.TicketRequest;
import com.tickets.support.dto.TicketResponse;
import com.tickets.support.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    // Handles JSON request (no files)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest ticketRequest, Principal principal) throws IOException {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // We use the new service method, passing null for files
        TicketResponse response = ticketService.createTicketWithAttachments(ticketRequest, null, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Handles Multipart request (with files)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TicketResponse> createTicketWithAttachments(
            @RequestPart("data") String ticketRequestJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            Principal principal) throws IOException {

        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        TicketRequest ticketRequest = objectMapper.readValue(ticketRequestJson, TicketRequest.class);
        TicketResponse response = ticketService.createTicketWithAttachments(ticketRequest, files, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TicketResponse>> getUserTickets(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {

        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Page<TicketResponse> tickets = ticketService.getUserTicketsPaginated(principal.getName(), page, size, search, status, priority);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        TicketResponse ticket = ticketService.getTicketById(id, principal.getName());
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id, @RequestBody TicketRequest ticketRequest, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        TicketResponse updatedTicket = ticketService.updateTicket(id, ticketRequest, principal.getName());
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ticketService.deleteTicket(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
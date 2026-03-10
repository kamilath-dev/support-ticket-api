package com.tickets.support.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TicketRequest {
    @NotBlank
    @Size(max = 100)
    private String title;
    
    @Size(max = 1000)
    private String description;
    
    private String priority;
}
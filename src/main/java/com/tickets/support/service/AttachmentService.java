package com.tickets.support.service;

import com.tickets.support.model.Attachment;
import com.tickets.support.model.Ticket;
import com.tickets.support.repository.AttachmentRepository;
import com.tickets.support.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;


@Service
public class AttachmentService {
    
    @Autowired
    private AttachmentRepository attachmentRepository;
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    public Attachment saveAttachment(MultipartFile file, Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        
        try {
            return fileStorageService.storeFile(file, ticket);
        } catch (IOException e) {
            throw new RuntimeException("Could not store file", e);
        }
    }
    
    public Attachment getAttachment(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));
    }

    public Resource loadFileAsResource(Long id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));
        
        try {
            Path filePath = Paths.get(attachment.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load file", e);
        }
    }
}
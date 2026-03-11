package com.tickets.support.controller;

import com.tickets.support.model.Attachment;
import com.tickets.support.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    
    @Autowired
    private AttachmentService attachmentService;
    
    @PostMapping("/upload/{ticketId}")
    public ResponseEntity<Attachment> uploadFile(
            @PathVariable Long ticketId,
            @RequestParam("file") MultipartFile file) {
        
        Attachment attachment = attachmentService.saveAttachment(file, ticketId);
        return ResponseEntity.ok(attachment);
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Resource resource = attachmentService.loadFileAsResource(id);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
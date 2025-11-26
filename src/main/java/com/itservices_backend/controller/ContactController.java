package com.itservices_backend.controller;

import com.itservices_backend.model.ContactMessage;
import com.itservices_backend.service.ContactService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")

public class ContactController {

    private final ContactService contactService;
    
    public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}

	@PostMapping("/send")
    public ResponseEntity<ContactMessage> sendMessage(@RequestBody ContactMessage message) {
        return ResponseEntity.ok(contactService.saveMessage(message));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        return ResponseEntity.ok(contactService.getAllMessages());
    }
}

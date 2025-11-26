package com.itservices_backend.service;

import com.itservices_backend.model.ContactMessage;
import com.itservices_backend.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ContactService {

    private final ContactRepository contactRepository;

    
    
    public ContactService(ContactRepository contactRepository) {
		super();
		this.contactRepository = contactRepository;
	}

	public ContactMessage saveMessage(ContactMessage message) {
        return contactRepository.save(message);
    }

    public List<ContactMessage> getAllMessages() {
        return contactRepository.findAll();
    }
}

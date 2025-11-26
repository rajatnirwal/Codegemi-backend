package com.itservices_backend.service;

import com.itservices_backend.model.ServiceDetail;
import com.itservices_backend.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ServiceService {

    private final ServiceRepository serviceRepository;
    
   
    public ServiceService(ServiceRepository serviceRepository) {
		super();
		this.serviceRepository = serviceRepository;
	}

	public ServiceDetail addService(ServiceDetail serviceDetail) {
        return serviceRepository.save(serviceDetail);
    }

    public List<ServiceDetail> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<ServiceDetail> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}

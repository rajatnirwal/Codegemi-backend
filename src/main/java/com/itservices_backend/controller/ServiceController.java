package com.itservices_backend.controller;

import com.itservices_backend.model.ServiceDetail;
import com.itservices_backend.service.ServiceService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;
    
    
    public ServiceController(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	@PostMapping("/add")
    public ResponseEntity<ServiceDetail> addService(@RequestBody ServiceDetail serviceDetail) {
        return ResponseEntity.ok(serviceService.addService(serviceDetail));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceDetail>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDetail> getById(@PathVariable Long id) {
        return serviceService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok("Service deleted");
    }
}

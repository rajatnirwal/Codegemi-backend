package com.itservices_backend.repository;
import com.itservices_backend.model.ServiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceDetail, Long> {
}

package com.rofix.enotes_service.repository;

import com.rofix.enotes_service.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDetailsRepository extends JpaRepository<FileDetails, Long> {
}

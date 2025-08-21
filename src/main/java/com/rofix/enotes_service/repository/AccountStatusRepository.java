package com.rofix.enotes_service.repository;

import com.rofix.enotes_service.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepository extends JpaRepository<AccountStatus,Long> {
}

package com.rofix.enotes_service.repository;

import com.rofix.enotes_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByMobileNoIgnoreCase(String mobileNo);

    Optional<User> findByIdAndStatus_VerificationCodeAndStatus_IsActiveIsFalse(Long id, String statusVerificationCode);

    Optional<User> findByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.id = :userId AND user.status.passwordResetToken = :code AND user.status.isActive IS TRUE")
    Optional<User> findByIdAndPasswordResetToken(@Param("userId") Long userId, @Param("code") String code);
}

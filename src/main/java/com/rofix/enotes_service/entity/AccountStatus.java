package com.rofix.enotes_service.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "AccountStatus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private Boolean isActive = false;

    @Column(nullable = true)
    private String verificationCode;

    @Column(nullable = true)
    private String passwordResetToken;

    @Column(nullable = true, insertable = false)
    private Instant passwordResetExpiry;


}

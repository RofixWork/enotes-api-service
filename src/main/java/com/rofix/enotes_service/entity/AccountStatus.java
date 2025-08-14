package com.rofix.enotes_service.entity;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String verificationCode;
}

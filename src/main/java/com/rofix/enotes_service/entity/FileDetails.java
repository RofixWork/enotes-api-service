package com.rofix.enotes_service.entity;

import com.rofix.enotes_service.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "FileDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class FileDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String displayFileName;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String uploadFileName;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private Long fileSize;
}

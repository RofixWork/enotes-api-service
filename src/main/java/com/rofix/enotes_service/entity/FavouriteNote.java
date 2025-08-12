package com.rofix.enotes_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "favourite_notes")
@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    private Note note;

    @Column(name = "user_id")
    private Integer user;
}

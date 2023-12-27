package com.mooko.dev.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "day_photos")
public class DayPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "thumbnail", nullable = false)
    private boolean thumbnail;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id")
    private Day day;

    @Builder
    public DayPhoto(String imageUrl, boolean thumbnail, Day day) {
        this.imageUrl = imageUrl;
        this.thumbnail = thumbnail;
        this.day = day;
        this.createdDate = LocalDate.now();
    }
}

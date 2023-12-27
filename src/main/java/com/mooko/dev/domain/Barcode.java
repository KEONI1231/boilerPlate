package com.mooko.dev.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.LongConsumer;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "barcodes")
public class Barcode {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "barcode_url", nullable = false)
    private String barcodeUrl;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BarcodeType type;

    @Column(name = "loading", nullable = false)
    private boolean loading;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id")
    private Day day;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Builder
    public Barcode(String barcodeUrl, String title, LocalDate startDate, LocalDate endDate, BarcodeType type, boolean loading, User user, Event event, Day day) {
        this.barcodeUrl = barcodeUrl;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.loading = loading;
        this.user = user;
        this.event = event;
        this.day = day;
        this.createdDate = LocalDate.now();
    }

}

package com.customer.reservation;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "table_number", nullable = false)
    private int tableNumber;

    @Column(name = "reservation_time", nullable = false)
    private LocalDateTime reservationTime;

    // Добавьте другие поля по необходимости

    public Reservation() {
        // Конструктор по умолчанию
    }
}

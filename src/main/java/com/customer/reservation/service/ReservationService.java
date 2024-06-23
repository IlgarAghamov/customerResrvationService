package com.customer.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    public Reservation createReservation(Long userId, int tableNumber, LocalDateTime reservationTime) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с ID " + userId + " не найден");
        }

        User user = userOptional.get();
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setTableNumber(tableNumber);
        reservation.setReservationTime(reservationTime);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Reservation updateReservation(Long reservationId, int newTableNumber, LocalDateTime newReservationTime) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            throw new IllegalArgumentException("Резервация с ID " + reservationId + " не найдена");
        }

        Reservation reservation = reservationOptional.get();
        reservation.setTableNumber(newTableNumber);
        reservation.setReservationTime(newReservationTime);

        return reservationRepository.save(reservation);
    }
}

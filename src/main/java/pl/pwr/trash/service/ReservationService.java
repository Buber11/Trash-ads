package pl.pwr.trash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pwr.trash.dao.ListingDao;
import pl.pwr.trash.dao.ReservationDao;
import pl.pwr.trash.model.Reservation;
import pl.pwr.trash.model.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ListingDao listingDao;

    public List<Reservation> getReservationsForUser(int userId) {
        return reservationDao.findByUserId(userId);
    }

    public void createReservation(int listingId, int userId, BigDecimal orderPrice) {
        Reservation reservation = new Reservation(
                null,
                listingId,
                userId,
                ReservationStatus.PENDING,
                orderPrice,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        reservationDao.save(reservation);
    }

    public void confirmReservation(Long reservationId) {
        Optional<Reservation> optional = reservationDao.findById(reservationId);
        if (optional.isPresent()) {
            Reservation reservation = optional.get();
            reservation.setStatusRes(ReservationStatus.CONFIRMED);
            reservation.setUpdatedAt(LocalDateTime.now());
            reservationDao.update(reservation);
            listingDao.updateStatus(reservation.getListingId(), "inactive");
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }

    public void cancelReservation(Long reservationId) {
        Optional<Reservation> optional = reservationDao.findById(reservationId);
        if (optional.isPresent()) {
            Reservation reservation = optional.get();
            reservation.setStatusRes(ReservationStatus.CANCELED);
            reservation.setUpdatedAt(LocalDateTime.now());
            reservationDao.update(reservation);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }
}

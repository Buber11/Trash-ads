package pl.pwr.trash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.trash.service.ReservationService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestParam int listingId,
                                                    @RequestParam int userId,
                                                    @RequestParam BigDecimal orderPrice) {
        reservationService.createReservation(listingId, userId, orderPrice);
        return ResponseEntity.ok("Reservation created");
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirmReservation(@PathVariable Long id) {
        reservationService.confirmReservation(id);
        return ResponseEntity.ok("Reservation confirmed");
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok("Reservation canceled");
    }
}


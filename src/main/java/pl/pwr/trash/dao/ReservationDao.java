package pl.pwr.trash.dao;

import lombok.RequiredArgsConstructor;
import pl.pwr.trash.model.Reservation;
import pl.pwr.trash.model.ReservationStatus;
import pl.pwr.trash.rowmapper.ReservationRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final ReservationRowMapper rowMapper;

    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservations";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        List<Reservation> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.stream().findFirst();
    }

    public List<Reservation> findByUserId(Integer userId) {
        String sql = "SELECT * FROM reservations WHERE user_id = ?";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public int save(Reservation reservation) {
        String sql = """
            INSERT INTO reservations (listing_id, user_id, status_res, order_price, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        return jdbcTemplate.update(sql,
                reservation.getListingId(),
                reservation.getUserId(),
                reservation.getStatusRes().name(),
                reservation.getOrderPrice(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt()
        );
    }

    public int update(Reservation reservation) {
        String sql = """
            UPDATE reservations
            SET listing_id = ?, user_id = ?, status_res = ?, order_price = ?, created_at = ?, updated_at = ?
            WHERE id = ?
        """;
        return jdbcTemplate.update(sql,
                reservation.getListingId(),
                reservation.getUserId(),
                reservation.getStatusRes().name(),
                reservation.getOrderPrice(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt(),
                reservation.getId()
        );
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservations WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

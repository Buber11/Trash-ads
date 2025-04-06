package pl.pwr.trash.rowmapper;

import pl.pwr.trash.model.Reservation;
import pl.pwr.trash.model.ReservationStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class ReservationRowMapper implements RowMapper<Reservation> {
    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Reservation(
                rs.getLong("id"),
                rs.getInt("listing_id"),
                rs.getInt("user_id"),
                ReservationStatus.valueOf(rs.getString("status_res")),
                rs.getBigDecimal("order_price"),
                rs.getObject("created_at", LocalDateTime.class),
                rs.getObject("updated_at", LocalDateTime.class)
        );
    }
}

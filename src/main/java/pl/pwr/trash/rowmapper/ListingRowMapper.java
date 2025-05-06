package pl.pwr.trash.rowmapper;

import pl.pwr.trash.model.Listing;
import pl.pwr.trash.model.ListingStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public class ListingRowMapper implements RowMapper<Listing> {
    @Override
    public Listing mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Listing(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getString("photo"),
                rs.getInt("user_id"),
                ListingStatus.valueOf(rs.getString("status_lis").toUpperCase()),
                rs.getObject("created_at", OffsetDateTime.class),
                rs.getObject("updated_at", OffsetDateTime.class)
        );
    }
}

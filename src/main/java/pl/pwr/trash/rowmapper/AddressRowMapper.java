package pl.pwr.trash.rowmapper;

import pl.pwr.trash.model.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Address(
                rs.getLong("id"),
                rs.getString("state"),
                rs.getString("city"),
                rs.getString("street"),
                rs.getString("house_number")
        );
    }
}

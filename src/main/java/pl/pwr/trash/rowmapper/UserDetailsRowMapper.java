package pl.pwr.trash.rowmapper;

import pl.pwr.trash.model.UserDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailsRowMapper implements RowMapper<UserDetails> {
    @Override
    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserDetails(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone")
        );
    }
}

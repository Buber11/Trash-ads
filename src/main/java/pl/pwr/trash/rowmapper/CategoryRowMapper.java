package pl.pwr.trash.rowmapper;

import pl.pwr.trash.model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Category(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
}

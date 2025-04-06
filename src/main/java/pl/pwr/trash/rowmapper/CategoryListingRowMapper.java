package pl.pwr.trash.rowmapper;

import pl.pwr.trash.model.CategoryListing;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CategoryListingRowMapper implements RowMapper<CategoryListing> {
    @Override
    public CategoryListing mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoryListing cl = new CategoryListing();
        cl.setListingId(rs.getInt("listing_id"));
        cl.setCategoryId(rs.getInt("category_id"));
        cl.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        cl.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return cl;
    }
}

package pl.pwr.trash.dao;

import pl.pwr.trash.model.CategoryListing;
import pl.pwr.trash.rowmapper.CategoryListingRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryListingDao {

    private final JdbcTemplate jdbcTemplate;
    private final CategoryListingRowMapper rowMapper = new CategoryListingRowMapper();

    public CategoryListingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CategoryListing> findAll() {
        String sql = "SELECT * FROM category_listing";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<CategoryListing> findByListingId(int listingId) {
        String sql = "SELECT * FROM category_listing WHERE listing_id = ?";
        return jdbcTemplate.query(sql, rowMapper, listingId);
    }

    public List<CategoryListing> findByCategoryId(int categoryId) {
        String sql = "SELECT * FROM category_listing WHERE category_id = ?";
        return jdbcTemplate.query(sql, rowMapper, categoryId);
    }

    public int save(CategoryListing cl) {
        String sql = """
            INSERT INTO category_listing (listing_id, category_id, created_at, updated_at)
            VALUES (?, ?, ?, ?)
        """;
        return jdbcTemplate.update(sql,
                cl.getListingId(),
                cl.getCategoryId(),
                cl.getCreatedAt(),
                cl.getUpdatedAt()
        );
    }

    public int update(CategoryListing cl) {
        String sql = """
            UPDATE category_listing
            SET created_at = ?, updated_at = ?
            WHERE listing_id = ? AND category_id = ?
        """;
        return jdbcTemplate.update(sql,
                cl.getCreatedAt(),
                cl.getUpdatedAt(),
                cl.getListingId(),
                cl.getCategoryId()
        );
    }

    public int delete(int listingId, int categoryId) {
        String sql = "DELETE FROM category_listing WHERE listing_id = ? AND category_id = ?";
        return jdbcTemplate.update(sql, listingId, categoryId);
    }
}

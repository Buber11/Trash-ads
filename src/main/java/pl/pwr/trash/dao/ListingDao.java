package pl.pwr.trash.dao;

import pl.pwr.trash.model.Listing;
import pl.pwr.trash.model.ListingStatus;
import pl.pwr.trash.rowmapper.ListingRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ListingDao {

    private final JdbcTemplate jdbcTemplate;
    private final ListingRowMapper rowMapper = new ListingRowMapper();

    public ListingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Listing> findAll() {
        String sql = "SELECT * FROM listings";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Listing> findById(Long id) {
        String sql = "SELECT * FROM listings WHERE id = ?";
        List<Listing> result = jdbcTemplate.query(sql, rowMapper, id);
        return result.stream().findFirst();
    }

    public List<Listing> findByUserId(Integer userId) {
        String sql = "SELECT * FROM listings WHERE user_id = ?";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public int save(Listing listing) {
        String sql = """
            INSERT INTO listings (title, description, price, photo, user_id, status_lis, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        return jdbcTemplate.update(sql,
                listing.getTitle(),
                listing.getDescription(),
                listing.getPrice(),
                listing.getPhoto(),
                listing.getUserId(),
                listing.getStatusLis().name(),
                listing.getCreatedAt(),
                listing.getUpdatedAt()
        );
    }

    public int update(Listing listing) {
        String sql = """
            UPDATE listings
            SET title = ?, description = ?, price = ?, photo = ?, user_id = ?, status_lis = ?, created_at = ?, updated_at = ?
            WHERE id = ?
        """;
        return jdbcTemplate.update(sql,
                listing.getTitle(),
                listing.getDescription(),
                listing.getPrice(),
                listing.getPhoto(),
                listing.getUserId(),
                listing.getStatusLis().name(),
                listing.getCreatedAt(),
                listing.getUpdatedAt(),
                listing.getId()
        );
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM listings WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

package pl.pwr.trash.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.pwr.trash.model.Listing;
import pl.pwr.trash.model.ListingStatus;
import pl.pwr.trash.rowmapper.ListingRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ListingDao {

    private final JdbcTemplate jdbcTemplate;
    private final ListingRowMapper rowMapper;


    public List<Listing> findAll() {
        String sql = "SELECT * FROM listings";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Listing> findById(Long id) {
        String sql = "SELECT * FROM listings WHERE id = ?";
        List<Listing> result = jdbcTemplate.query(sql, rowMapper, id);
        return result.stream().findFirst();
    }

    public Page<Listing> findByUserId(Integer userId, Pageable pageable) {
        String sql = "SELECT * FROM listings WHERE user_id = ? LIMIT ? OFFSET ?";
        List<Listing> listings = jdbcTemplate.query(
                sql,
                rowMapper,
                userId,
                pageable.getPageSize(),
                pageable.getOffset()
        );

        String countSql = "SELECT COUNT(*) FROM listings WHERE user_id = ?";
        Integer total = jdbcTemplate.queryForObject(countSql, Integer.class, userId);

        return new PageImpl<>(listings, pageable, total);
    }

    public int save(Listing listing) {
        String sql = """
            INSERT INTO listings (title, description, price, photo, user_id, status_lis, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?::listing_status, ?, ?)
        """;
        return jdbcTemplate.update(sql,
                listing.getTitle(),
                listing.getDescription(),
                listing.getPrice(),
                listing.getPhoto(),
                listing.getUserId(),
                listing.getStatusLis().getStatus(),
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
                listing.getStatusLis().getStatus(),
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

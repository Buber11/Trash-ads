package pl.pwr.trash.dao;

import pl.pwr.trash.model.UserDetails;
import pl.pwr.trash.rowmapper.UserDetailsRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDetailsDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserDetailsRowMapper rowMapper = new UserDetailsRowMapper();

    public UserDetailsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserDetails> findAll() {
        String sql = "SELECT * FROM user_details";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<UserDetails> findById(Long id) {
        String sql = "SELECT * FROM user_details WHERE id = ?";
        List<UserDetails> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.stream().findFirst();
    }

    public Optional<UserDetails> findByUserId(Long userId) {
        String sql = "SELECT * FROM user_details WHERE user_id = ?";
        List<UserDetails> list = jdbcTemplate.query(sql, rowMapper, userId);
        return list.stream().findFirst();
    }

    public int save(UserDetails details) {
        String sql = """
            INSERT INTO user_details (user_id, first_name, last_name, phone)
            VALUES (?, ?, ?, ?)
        """;
        return jdbcTemplate.update(sql,
                details.getUserId(),
                details.getFirstName(),
                details.getLastName(),
                details.getPhone()
        );
    }

    public int update(UserDetails details) {
        String sql = """
            UPDATE user_details
            SET user_id = ?, first_name = ?, last_name = ?, phone = ?
            WHERE id = ?
        """;
        return jdbcTemplate.update(sql,
                details.getUserId(),
                details.getFirstName(),
                details.getLastName(),
                details.getPhone(),
                details.getId()
        );
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM user_details WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

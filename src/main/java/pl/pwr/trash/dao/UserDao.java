package pl.pwr.trash.dao;

import pl.pwr.trash.model.User;
import pl.pwr.trash.rowmapper.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper rowMapper = new UserRowMapper();

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, rowMapper, id);
        return users.stream().findFirst();
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, rowMapper, email);
        return users.stream().findFirst();
    }

    public int save(User user) {
        String sql = """
            INSERT INTO users (email, password_hash, role, address_id, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        return jdbcTemplate.update(sql,
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole(),
                user.getAddressId(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public int update(User user) {
        String sql = """
            UPDATE users
            SET email = ?, password_hash = ?, role = ?, address_id = ?, created_at = ?, updated_at = ?
            WHERE id = ?
        """;
        return jdbcTemplate.update(sql,
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole(),
                user.getAddressId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getId()
        );
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

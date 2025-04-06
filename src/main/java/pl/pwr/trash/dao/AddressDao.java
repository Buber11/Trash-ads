package pl.pwr.trash.dao;

import pl.pwr.trash.model.Address;
import pl.pwr.trash.rowmapper.AddressRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AddressDao {

    private final JdbcTemplate jdbcTemplate;
    private final AddressRowMapper rowMapper = new AddressRowMapper();

    public AddressDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Address> findAll() {
        String sql = "SELECT * FROM address";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Address> findById(Long id) {
        String sql = "SELECT * FROM address WHERE id = ?";
        List<Address> addresses = jdbcTemplate.query(sql, rowMapper, id);
        return addresses.stream().findFirst();
    }

    public int save(Address address) {
        String sql = """
            INSERT INTO address (state, city, street, house_number)
            VALUES (?, ?, ?, ?)
        """;
        return jdbcTemplate.update(sql,
                address.getState(),
                address.getCity(),
                address.getStreet(),
                address.getHouseNumber()
        );
    }

    public int update(Address address) {
        String sql = """
            UPDATE address
            SET state = ?, city = ?, street = ?, house_number = ?
            WHERE id = ?
        """;
        return jdbcTemplate.update(sql,
                address.getState(),
                address.getCity(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getId()
        );
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM address WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

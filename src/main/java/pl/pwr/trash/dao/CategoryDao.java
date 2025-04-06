package pl.pwr.trash.dao;

import pl.pwr.trash.model.Category;
import pl.pwr.trash.rowmapper.CategoryRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;
    private final CategoryRowMapper rowMapper = new CategoryRowMapper();

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> findAll() {
        String sql = "SELECT * FROM categories";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Category> findById(Long id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        List<Category> categories = jdbcTemplate.query(sql, rowMapper, id);
        return categories.stream().findFirst();
    }

    public Optional<Category> findByName(String name) {
        String sql = "SELECT * FROM categories WHERE name = ?";
        List<Category> categories = jdbcTemplate.query(sql, rowMapper, name);
        return categories.stream().findFirst();
    }

    public int save(Category category) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        return jdbcTemplate.update(sql, category.getName());
    }

    public int update(Category category) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, category.getName(), category.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

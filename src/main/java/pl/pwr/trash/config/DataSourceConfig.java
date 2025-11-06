package pl.pwr.trash.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.AbstractDataSource;
import pl.pwr.trash.ReadReplicaRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Configuration
public class DataSourceConfig {

    @Value("${db.master.url}")
    private String masterUrl;

    @Value("${db.replica1.url}") private String r1;
    @Value("${db.replica2.url}") private String r2;
    @Value("${db.replica3.url}") private String r3;
    @Value("${db.replica4.url}") private String r4;
    @Value("${db.replica5.url}") private String r5;
    @Value("${db.replica6.url}") private String r6;
    @Value("${db.replica7.url}") private String r7;
    @Value("${db.replica8.url}") private String r8;
    @Value("${db.replica9.url}") private String r9;
    @Value("${db.replica10.url}") private String r10;

    @Value("${db.master.username}")
    private String username;

    @Value("${db.master.password}")
    private String password;

    @Value("${db.driver-class-name}")
    private String driverClassName;

    private DataSource buildDataSource(String url) {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DataSource master = buildDataSource(masterUrl);
        List<DataSource> replicas = List.of(
                buildDataSource(r1), buildDataSource(r2)
        );
        return new ReadReplicaRoutingDataSource(master, replicas);
    }


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource); // master do zapisu
    }

    @Bean(name = "readJdbcTemplate")
    public JdbcTemplate readJdbcTemplate(DataSource dataSource) {
        DataSource readOnlyDataSource = new AbstractDataSource() {
            @Override
            public Connection getConnection() throws SQLException {
                return ((ReadReplicaRoutingDataSource) dataSource).getReadOnlyConnection();
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                return ((ReadReplicaRoutingDataSource) dataSource).getReadOnlyConnection();
            }
        };

        return new JdbcTemplate(readOnlyDataSource); // SELECTy na replikach
    }

}

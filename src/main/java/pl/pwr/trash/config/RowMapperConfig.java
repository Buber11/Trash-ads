package pl.pwr.trash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.pwr.trash.rowmapper.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RowMapperConfig {

    @Bean
    public AddressRowMapper addressRowMapper() {
        return new AddressRowMapper();
    }

    @Bean
    public CategoryListingRowMapper categoryListingRowMapper() {
        return new CategoryListingRowMapper();
    }

    @Bean
    public CategoryRowMapper categoryRowMapper() {
        return new CategoryRowMapper();
    }

    @Bean
    public ListingRowMapper listingRowMapper() {
        return new ListingRowMapper();
    }

    @Bean
    public ReservationRowMapper reservationRowMapper() {
        return new ReservationRowMapper();
    }

    @Bean
    public UserDetailsRowMapper userDetailsRowMapper() {
        return new UserDetailsRowMapper();
    }

    @Bean
    public UserRowMapper userRowMapper() {
        return new UserRowMapper();
    }

}




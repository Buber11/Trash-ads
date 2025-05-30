package pl.pwr.trash.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.pwr.trash.dao.UserDao;
import pl.pwr.trash.model.User;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import java.util.Optional;


@Configuration
public class ApplicationConfig {

    private final UserDao userDao;

    public ApplicationConfig(UserDao userDao) {
        this.userDao = userDao;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            Optional<User> user = userDao.findByEmail(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            return user.get();
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
package pl.pwr.trash.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import pl.pwr.trash.model.User;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String token);

    Long extractUserId(String token);

    String extractToken(String authHeader);

    String extractToken(HttpServletRequest request);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    long getExpirationTime();

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    Cookie createJwtCookie(Map<String, Object> extraClaims,
                           User user,
                           String nameOfCookie);

}

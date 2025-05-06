package pl.pwr.trash.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.out.println("Request URI: " + requestURI);

        // Skip public endpoints
        if (Arrays.stream(SecurityConfiguration.PUBLIC_ENDPOINTS).anyMatch(requestURI::startsWith)) {
            System.out.println("Public endpoint accessed, skipping filter.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtService.extractToken(request);
        System.out.println("Extracted token: " + token);

        if (token != null) {
            try {
                final String username = jwtService.extractUsername(token);
                System.out.println("Extracted username: " + username);

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                System.out.println("Current authentication: " + authentication);

                if (username != null && authentication == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    System.out.println("Loaded user details: " + userDetails);

                    if (jwtService.isTokenValid(token, userDetails)) {
                        System.out.println("Token is valid.");
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        String userId = jwtService.extractUserId(token);
                        System.out.println("Extracted user ID: " + userId);
                        request.setAttribute("user_id", userId);
                    } else {
                        System.out.println("Token is invalid.");
                    }
                }
            } catch (Exception exception) {
                System.out.println("Exception occurred: " + exception.getMessage());
                exception.printStackTrace();
                handlerExceptionResolver.resolveException(request, response, null, exception);
                return;
            }
        } else {
            System.out.println("No token found in the request.");
        }

        System.out.println("Proceeding with the filter chain.");
        filterChain.doFilter(request, response);
    }
}
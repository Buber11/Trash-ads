package pl.pwr.trash.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pwr.trash.dao.UserDao;
import pl.pwr.trash.model.User;
import pl.pwr.trash.request.UserRequest;
import pl.pwr.trash.security.JwtService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void login(HttpServletResponse httpServletResponse, UserRequest userRequest) {
        User user = userDao.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        Cookie cookie = jwtService.createJwtCookie(
                Map.of("user_id", user.getId().toString()),
                user,
                "jwt_token");
        httpServletResponse.addCookie(cookie);
    }


    @Override
    public void register(UserRequest userRequest) {
        userService.createUser(userRequest);
    }
}

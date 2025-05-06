package pl.pwr.trash.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.pwr.trash.request.UserRequest;

public interface AuthService {

    void login(HttpServletResponse httpServletResponse,
               UserRequest userRequest);

    void register(UserRequest userRequest);

}

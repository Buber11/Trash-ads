package pl.pwr.trash.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import pl.pwr.trash.model.User;
import pl.pwr.trash.request.UserRequest;

import java.util.List;

public interface UserService {
    User getUser(HttpServletRequest httpServletRequest, Long id);
    List<User> getUsers(HttpServletRequest httpServletRequest);
    void removeUser(HttpServletRequest httpServletRequest, Long id);
    void updateUser(HttpServletRequest httpServletRequest,
                    Long id,
                    UserRequest userRequest);
    void createUser(HttpServletRequest httpServletRequest,
                    UserRequest userRequest);
}

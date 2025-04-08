package pl.pwr.trash.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pwr.trash.dao.UserDao;
import pl.pwr.trash.exception.AccessDeniedException;
import pl.pwr.trash.exception.ResourceNotFound;
import pl.pwr.trash.model.User;
import pl.pwr.trash.request.UserRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUser(HttpServletRequest httpServletRequest, Long id) {
        Long userId = (Long) httpServletRequest.getAttribute("user_id");
        User user = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFound("user"));
        if (user.getId().equals(userId)) {
            return user;
        } else {
            throw new AccessDeniedException("Access denied for user: " + userId);
        }
    }

    @Override
    public List<User> getUsers(HttpServletRequest httpServletRequest) {
        return userDao.findAll();
    }

    @Override
    public void removeUser(HttpServletRequest httpServletRequest, Long id) {
        User user = getUser(httpServletRequest, id);
        userDao.deleteById(user.getId());
    }

    @Override
    public void updateUser(HttpServletRequest httpServletRequest,
                           Long id,
                           UserRequest userRequest) {
        User user = getUser(httpServletRequest, id);

        if (! userRequest.getPassword().isEmpty()){
            user.setPasswordHash( passwordEncoder.encode(userRequest.getPassword()));
        }
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());

        userDao.save(user);
    }

    @Override
    public void createUser(HttpServletRequest httpServletRequest,
                           UserRequest userRequest) {
        User user = User.builder()
                .email(userRequest.getEmail())
                .passwordHash(passwordEncoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .build();

        userDao.save(user);
    }
}
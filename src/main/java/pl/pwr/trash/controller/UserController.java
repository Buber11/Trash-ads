package pl.pwr.trash.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.trash.model.User;
import pl.pwr.trash.request.UserRequest;
import pl.pwr.trash.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(
            HttpServletRequest httpRequest,
            @PathVariable("id")Long id
    ){
        User user = userService.getUser(httpRequest,id);
        return ResponseEntity.ok(user);
    }
    @GetMapping
    public ResponseEntity<List<User>> getUsers(
            HttpServletRequest httpRequest
    ){
        List<User> users = userService.getUsers(httpRequest);
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(
            HttpServletRequest httpRequest,
            @PathVariable("id")Long id
    ){
        userService.removeUser(httpRequest, id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity updateUser(
            HttpServletRequest httpRequest,
            @PathVariable("id")Long id,
            @RequestBody @Valid UserRequest userRequest
    ){
        userService.updateUser(httpRequest,id,userRequest);
        return ResponseEntity.noContent().build();
    }
    @PostMapping()
    public ResponseEntity createUser(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid UserRequest userRequest
    ){
        userService.createUser(httpServletRequest, userRequest);
        return ResponseEntity.noContent().build();
    }
}

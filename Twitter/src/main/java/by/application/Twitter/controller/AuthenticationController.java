package by.application.Twitter.controller;

import by.application.Twitter.model.LoginDetails;
import by.application.Twitter.model.User;
import by.application.Twitter.security.JWTUtil;
import by.application.Twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtTokenUtil;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginDetails loginDetails) {
        String token = null;
        if (userService.existUserByCredentials(loginDetails)) token = jwtTokenUtil
                .generateToken(new LoginDetails(loginDetails.getUsername(), loginDetails.getPassword()));

        return token != null
                ? new ResponseEntity(token, HttpStatus.CREATED)
                : new ResponseEntity("Username or password is incorrect", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> singUp(@RequestBody User user) {
        userService.createUser(user);
        String token = jwtTokenUtil.generateToken(new LoginDetails(user.getUsername(), user.getPassword()));

        return token != null
                ? new ResponseEntity(token, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

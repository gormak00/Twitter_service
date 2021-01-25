package by.application.Twitter.controller;

import by.application.Twitter.controller.dataTransferObject.LoginDetailsDto;
import by.application.Twitter.controller.dataTransferObject.LoginDetailsMapper;
import by.application.Twitter.controller.dataTransferObject.UserDto;
import by.application.Twitter.controller.dataTransferObject.UserMapper;
import by.application.Twitter.model.LoginDetails;
import by.application.Twitter.model.User;
import by.application.Twitter.config.security.JWTUtil;
import by.application.Twitter.service.GoogleAuthService;
import by.application.Twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtTokenUtil;
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private GoogleAuthService googleAuthService;

    private static final String authorizationRequestBaseUri = "oauth2/authorize-client";
    private Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    @PostMapping(value = "/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginDetailsDto loginDetailsDto) {
        LoginDetails loginDetails = LoginDetailsMapper.toLoginDetails(loginDetailsDto);
        String token = null;
        if (userService.existUserByCredentials(loginDetails) && userService.isUserActivated(loginDetails.getUsername()))
            token = jwtTokenUtil
                    .generateToken(new LoginDetails(loginDetails.getUsername(), loginDetails.getPassword()));

        return token != null
                ? new ResponseEntity(token, HttpStatus.CREATED)
                : new ResponseEntity("Username or password is incorrect", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> singUp(@Valid @RequestBody UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        userService.createUser(user);
        String token = jwtTokenUtil.generateToken(new LoginDetails(user.getUsername(), user.getPassword()));

        return token != null
                ? new ResponseEntity(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activate(@PathVariable(name = "code") String code) {
        String token = userService.activateUser(code);

        return token != null
                ? new ResponseEntity(token, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "login";
    }

    @GetMapping(value = "/loginSuccess")
    public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
        Map userAttributes = googleAuthService.getUserAttributes(authentication);
        User user = googleAuthService.loadUser(userAttributes);
        userService.saveUserFromGoogle(user);
        model.addAttribute("name", user.getFirstName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
        return "loginSuccess";
    }
}

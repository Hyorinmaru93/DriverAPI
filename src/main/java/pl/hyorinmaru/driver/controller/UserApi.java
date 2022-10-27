package pl.hyorinmaru.driver.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.hyorinmaru.driver.dto.AuthRequest;
import pl.hyorinmaru.driver.model.User;

@RestController
public class UserApi {

    private final AuthenticationManager authorizationManager;

    public UserApi(AuthenticationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    @PostMapping("/auth/login")
    public String getJwt(@RequestBody AuthRequest authRequest){

        Authentication authenticate = authorizationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        User principal = (User) authenticate.getPrincipal();

        System.out.println(principal);

        return "";
    }
}
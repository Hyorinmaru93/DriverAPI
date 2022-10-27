package pl.hyorinmaru.driver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.hyorinmaru.driver.dto.AuthRequest;
import pl.hyorinmaru.driver.repository.AppUserRepository;

@RestController
@RequiredArgsConstructor
public class UserApi {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/login")
    public String getJwt(@RequestBody AuthRequest authRequest){

//        authenticationManager.authenticate();


        return "";
    }
}

package pl.hyorinmaru.driver.controller;

import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.hyorinmaru.driver.dto.AuthRequest;

@RestController
public class UserApi {

    AuthorizationManager authorizationManager;

    @PostMapping("/auth/login")
    public String getJwt(@RequestBody AuthRequest authRequest){



        return "";
    }
}

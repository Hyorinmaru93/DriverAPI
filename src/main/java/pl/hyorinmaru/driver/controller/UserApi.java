package pl.hyorinmaru.driver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.hyorinmaru.driver.dto.AuthRequest;
import pl.hyorinmaru.driver.model.AppUser;

@RestController
public class UserApi {

    private final AuthenticationManager authenticationManager;

    public UserApi(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth/login")
    public String getJwt(@RequestBody AuthRequest authRequest){
        System.out.println("Cokolwiek");
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getEmail()));

        AppUser principal = (AppUser) authenticate.getPrincipal();

        System.out.println(principal);

        return "";
    }
}

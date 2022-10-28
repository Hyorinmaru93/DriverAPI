package pl.hyorinmaru.driver.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.hyorinmaru.driver.dto.AuthRequest;
import pl.hyorinmaru.driver.dto.AuthResponse;
import pl.hyorinmaru.driver.model.User;

@RestController
public class UserApi {

    private final AuthenticationManager authorizationManager;

    public UserApi(AuthenticationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> getJwt(@RequestBody AuthRequest authRequest) {

        try {
            Authentication authenticate = authorizationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            User principal = (User) authenticate.getPrincipal();

            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withSubject(principal.getUsername())
                    .withIssuer("Hyorinmaru")
                    .withClaim("isAdmin", true)
                    .sign(algorithm);
            AuthResponse authResponse = new AuthResponse(principal.getUsername(), token);
            return ResponseEntity.ok(authResponse);


        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
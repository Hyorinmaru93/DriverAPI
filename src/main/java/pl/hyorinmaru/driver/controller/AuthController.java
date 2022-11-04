package pl.hyorinmaru.driver.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.hyorinmaru.driver.dto.AuthRequest;
import pl.hyorinmaru.driver.dto.AuthResponse;
import pl.hyorinmaru.driver.model.User;
import pl.hyorinmaru.driver.service.UserService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authorizationManager;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> getJwt(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authorizationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            User user = (User) authenticate.getPrincipal();

            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuer("Hyorinmaru")
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);

            AuthResponse authResponse = new AuthResponse(user.getUsername(), token);
            return ResponseEntity.ok(authResponse);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/registry")
    public ResponseEntity<?> registry(@RequestBody AuthRequest authRequest){
        if (userService.isAlreadyRegistred(authRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("User with that email already exists!");
        }

        //toDo rejestracja


        return ResponseEntity.ok(String.format("User %s was registred", authRequest.getEmail()));
    }


}

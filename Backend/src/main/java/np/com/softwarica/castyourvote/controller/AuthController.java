package np.com.softwarica.castyourvote.controller;

import np.com.softwarica.castyourvote.pojo.ApiResponsePojo;
import np.com.softwarica.castyourvote.pojo.JwtAuthenticationResponsePojo;
import np.com.softwarica.castyourvote.pojo.LoginRequestPojo;
import np.com.softwarica.castyourvote.pojo.SignUpRequestPojo;
import np.com.softwarica.castyourvote.service.implementation.JwtTokenProviderService;
import np.com.softwarica.castyourvote.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final JwtTokenProviderService tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestPojo loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponsePojo(jwt,"Bearer"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestPojo signUpRequest) throws Exception {
        try {
            var user = userService.registerUser(signUpRequest);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/users/{username}")
                    .buildAndExpand(user.getUsername()).toUri();
            return ResponseEntity.created(location).body(new ApiResponsePojo(true, "User registered successfully"));
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponsePojo(false, ex.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
}

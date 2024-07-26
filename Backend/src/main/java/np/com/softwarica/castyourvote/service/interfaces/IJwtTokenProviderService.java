package np.com.softwarica.castyourvote.service.interfaces;

import org.springframework.security.core.Authentication;

public interface IJwtTokenProviderService {
    String generateToken(Authentication authentication);
    Long getUserIdFromJWT(String token);
    boolean validateToken(String authToken);
}
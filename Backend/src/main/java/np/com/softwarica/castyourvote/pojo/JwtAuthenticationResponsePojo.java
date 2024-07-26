package np.com.softwarica.castyourvote.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponsePojo {
    private String accessToken;
    private String tokenType = "Bearer";
    private String role;
}

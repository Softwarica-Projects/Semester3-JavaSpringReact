package np.com.softwarica.castyourvote.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestPojo {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

}

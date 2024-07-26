package np.com.softwarica.castyourvote.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceRequestPojo {
    @NotBlank
    @Size(max = 40)
    private String text;
}

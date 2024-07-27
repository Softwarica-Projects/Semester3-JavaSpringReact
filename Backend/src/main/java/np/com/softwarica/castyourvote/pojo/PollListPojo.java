package np.com.softwarica.castyourvote.pojo;

import lombok.*;
import np.com.softwarica.castyourvote.entity.Choice;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollListPojo {
    private Long id;
    private String question;
    private List<Choice> choices = new ArrayList<>();
    private Instant expirationDateTime;
}

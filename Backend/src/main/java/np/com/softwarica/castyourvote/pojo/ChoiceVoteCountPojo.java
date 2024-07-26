package np.com.softwarica.castyourvote.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChoiceVoteCountPojo {
    private Long choiceId;
    private Long voteCount;
}


package np.com.softwarica.castyourvote.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import np.com.softwarica.castyourvote.entity.Choice;
import np.com.softwarica.castyourvote.entity.Poll;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.assertj.core.api.Assertions;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.util.ArrayList;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@RequiredArgsConstructor
public class PollRepoTest {

    @Autowired
    private IPollRepository pollRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUser() {
        var choices = new ArrayList<Choice>();
        var choiceOne = new Choice();
        choiceOne.setText("Yes");
        choices.add(choiceOne);
        var choiceTwo = new Choice();
        choiceTwo.setText("No");
        choices.add(choiceTwo);
        Poll poll = Poll.builder()
                .question("Is Twitter better than facebook")
                .choices(choices)
                .expirationDateTime(Instant.now()).build();
        pollRepository.save(poll);
        Assertions.assertThat(poll.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void getUserTest() {
        var poll = pollRepository.findById(1L).get();
        Assertions.assertThat(poll.getId()).isEqualTo(1);
    }

    //
    @Test
    @Order(3)
    public void fetchAll() {
        var pollList = pollRepository.findAll();
        Assertions.assertThat(pollList.size()).isGreaterThan(0);
    }

    //
    @Test
    @Order(4)
    @Rollback(value = false)
    public void Update() {
        var poll = pollRepository.findById(1L).get();
        var newQuestion = "Is Instagram better than FB";
        poll.setQuestion(newQuestion);
        var updatedPoll = pollRepository.save(poll);
        Assertions.assertThat(updatedPoll.getQuestion()).isEqualTo(newQuestion);
    }

    //
    @Test
    @Order(5)
    @Rollback(value = false)
    public void Delete() {
        var poll = pollRepository.findById(1L).get();
        pollRepository.delete(poll);
        var deletedPoll = pollRepository.findById(1L);
        Assertions.assertThat(deletedPoll).isNull();
    }

}

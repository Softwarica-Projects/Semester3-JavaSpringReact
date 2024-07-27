package np.com.softwarica.castyourvote;

import io.cucumber.spring.CucumberContextConfiguration;
import np.com.softwarica.castyourvote.entity.Poll;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = Poll.class)
public class CucumberSpringConfiguration { }

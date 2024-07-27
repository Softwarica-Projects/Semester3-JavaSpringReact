package np.com.softwarica.castyourvote;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import np.com.softwarica.castyourvote.pojo.VoteRequestPojo;
import np.com.softwarica.castyourvote.repository.IPollRepository;
import np.com.softwarica.castyourvote.service.implementation.PollService;
import np.com.softwarica.castyourvote.service.implementation.UserDetailService;
import np.com.softwarica.castyourvote.service.interfaces.IPollService;
import np.com.softwarica.castyourvote.service.interfaces.IUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PollStepDefinitions {
    private static final Log log = LogFactory.getLog(PollStepDefinitions.class);
    @Autowired
    private IPollService pollService;
    @Autowired
    private IUserService userService;

    @Given("find all poll")
    public void find_all_poll() {
        var allPolls = pollService.GetAllPolls();
        log.info(allPolls);
        Assert.assertTrue(!allPolls.isEmpty());
    }

    @Given("find poll by id")
    public void find_poll_by_id() {
        pollService.GetPollById(1L);
        System.out.println("Poll fetched successfully");
    }

    @Then("can vote")
    public void can_vote() {
        var voteReq = new VoteRequestPojo(2L);
        var user = userService.getUserProfile("admin");
        var userDetail = new UserDetailService(user.getId(), user.getName(), user.getUsername(), "admin", "admin", new ArrayList<>());
        pollService.castVoteAndGetUpdatedPoll(1L, voteReq, userDetail);
        System.out.println("Vote by admin successfully");
    }


    @Given("Poll Data")
    public void poll_data() {
        var allPolls = pollService.GetAllPolls();
        log.info(allPolls);
        Assert.assertTrue(!allPolls.isEmpty());
    }

    @Given("User Detail")
    public void user_detail() {
        var user = userService.getUserProfile("admin");
        log.info(user);
    }

    @Then("Fetch Poll of the user")
    public void fetch_poll_of_the_user() {
        var user = userService.getUserProfile("admin");
        var userDetail = new UserDetailService(user.getId(), user.getName(), user.getUsername(), "admin", "admin", new ArrayList<>());
        var polls = pollService.getAllPolls(userDetail, 1, 10);
        log.info(polls);
    }
}

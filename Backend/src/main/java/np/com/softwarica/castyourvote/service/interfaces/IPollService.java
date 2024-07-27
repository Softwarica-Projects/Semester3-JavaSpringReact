package np.com.softwarica.castyourvote.service.interfaces;

import np.com.softwarica.castyourvote.entity.Poll;
import np.com.softwarica.castyourvote.pojo.*;
import np.com.softwarica.castyourvote.service.implementation.UserDetailService;

import java.util.ArrayList;


public interface IPollService {
    PagedResponsePojo<PollResponsePojo> getAllPolls(UserDetailService currentUser, int page, int size);
    PagedResponsePojo<PollResponsePojo> getPollsCreatedBy(String username, UserDetailService currentUser, int page, int size);
    PagedResponsePojo<PollResponsePojo> getPollsVotedBy(String username, UserDetailService currentUser, int page, int size);
    Poll createPoll(PollRequestPojo pollRequest);
    PollResponsePojo getPollById(Long pollId, UserDetailService currentUser);
    PollResponsePojo castVoteAndGetUpdatedPoll(Long pollId, VoteRequestPojo voteRequest, UserDetailService currentUser);
    ArrayList<PollListPojo> GetAllPolls();
    PollListPojo GetPollById(Long pollId);
}

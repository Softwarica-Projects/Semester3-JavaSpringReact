package np.com.softwarica.castyourvote.service.interfaces;

import np.com.softwarica.castyourvote.entity.Poll;
import np.com.softwarica.castyourvote.pojo.PagedResponsePojo;
import np.com.softwarica.castyourvote.pojo.PollRequestPojo;
import np.com.softwarica.castyourvote.pojo.PollResponsePojo;
import np.com.softwarica.castyourvote.pojo.VoteRequestPojo;
import np.com.softwarica.castyourvote.service.implementation.UserDetailService;


public interface IPollService {
    PagedResponsePojo<PollResponsePojo> getAllPolls(UserDetailService currentUser, int page, int size);
    PagedResponsePojo<PollResponsePojo> getPollsCreatedBy(String username, UserDetailService currentUser, int page, int size);
    PagedResponsePojo<PollResponsePojo> getPollsVotedBy(String username, UserDetailService currentUser, int page, int size);
    Poll createPoll(PollRequestPojo pollRequest);
    PollResponsePojo getPollById(Long pollId, UserDetailService currentUser);
    PollResponsePojo castVoteAndGetUpdatedPoll(Long pollId, VoteRequestPojo voteRequest, UserDetailService currentUser);
    }

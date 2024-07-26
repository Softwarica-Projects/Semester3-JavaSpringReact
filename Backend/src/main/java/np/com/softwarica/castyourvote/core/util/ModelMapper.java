package np.com.softwarica.castyourvote.core.util;

import np.com.softwarica.castyourvote.entity.Poll;
import np.com.softwarica.castyourvote.entity.User;
import np.com.softwarica.castyourvote.pojo.ChoiceResponsePojo;
import np.com.softwarica.castyourvote.pojo.PollResponsePojo;
import np.com.softwarica.castyourvote.pojo.UserSummaryPojo;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {

    public static PollResponsePojo mapPollToPollResponse(Poll poll, Map<Long, Long> choiceVotesMap, User creator, Long userVote) {
        PollResponsePojo pollResponse = new PollResponsePojo();
        pollResponse.setId(poll.getId());
        pollResponse.setQuestion(poll.getQuestion());
        pollResponse.setCreationDateTime(poll.getCreatedAt());
        pollResponse.setExpirationDateTime(poll.getExpirationDateTime());
        Instant now = Instant.now();
        pollResponse.setIsExpired(poll.getExpirationDateTime().isBefore(now));

        List<ChoiceResponsePojo> choiceResponses = poll.getChoices().stream().map(choice -> {
            ChoiceResponsePojo choiceResponse = new ChoiceResponsePojo();
            choiceResponse.setId(choice.getId());
            choiceResponse.setText(choice.getText());

            if(choiceVotesMap.containsKey(choice.getId())) {
                choiceResponse.setVoteCount(choiceVotesMap.get(choice.getId()));
            } else {
                choiceResponse.setVoteCount(0);
            }
            return choiceResponse;
        }).collect(Collectors.toList());

        pollResponse.setChoices(choiceResponses);
        UserSummaryPojo creatorSummary = new UserSummaryPojo(creator.getId(), creator.getUsername(), creator.getName());
        pollResponse.setCreatedBy(creatorSummary);

        if(userVote != null) {
            pollResponse.setSelectedChoice(userVote);
        }

        long totalVotes = pollResponse.getChoices().stream().mapToLong(ChoiceResponsePojo::getVoteCount).sum();
        pollResponse.setTotalVotes(totalVotes);

        return pollResponse;
    }

}

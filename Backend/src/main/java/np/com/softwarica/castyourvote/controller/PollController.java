package np.com.softwarica.castyourvote.controller;

import np.com.softwarica.castyourvote.entity.Poll;
import np.com.softwarica.castyourvote.pojo.*;
import np.com.softwarica.castyourvote.core.authconfig.CurrentUser;
import np.com.softwarica.castyourvote.service.implementation.UserDetailService;
import np.com.softwarica.castyourvote.core.util.AppConstants;
import np.com.softwarica.castyourvote.service.interfaces.IPollService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/polls")
public class PollController {
    private final IPollService pollService;

    @GetMapping
    public PagedResponsePojo<PollResponsePojo> getPolls(@CurrentUser UserDetailService currentUser,
                                                        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getAllPolls(currentUser, page, size);
    }

    @PostMapping
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequestPojo pollRequest) {
        Poll poll = pollService.createPoll(pollRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pollId}")
                .buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponsePojo(true, "Poll Created Successfully"));
    }

    @GetMapping("/{pollId}")
    public PollResponsePojo getPollById(@CurrentUser UserDetailService currentUser,
                                        @PathVariable Long pollId) {
        return pollService.getPollById(pollId, currentUser);
    }

    @PostMapping("/{pollId}/votes")
    @PreAuthorize("hasRole('USER')")
    public PollResponsePojo castVote(@CurrentUser UserDetailService currentUser,
                                     @PathVariable Long pollId,
                                     @Valid @RequestBody VoteRequestPojo voteRequest) {
        return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
    }

}

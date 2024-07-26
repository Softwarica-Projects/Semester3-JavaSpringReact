package np.com.softwarica.castyourvote.controller;

import np.com.softwarica.castyourvote.pojo.*;
import np.com.softwarica.castyourvote.service.implementation.UserDetailService;
import np.com.softwarica.castyourvote.core.authconfig.CurrentUser;
import np.com.softwarica.castyourvote.core.util.AppConstants;
import np.com.softwarica.castyourvote.service.interfaces.IPollService;
import np.com.softwarica.castyourvote.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final IUserService userService;
    private final IPollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    public UserSummaryPojo getCurrentUser(@CurrentUser UserDetailService currentUser) {
        UserSummaryPojo userSummary = new UserSummaryPojo(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailabilityPojo checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userService.existsByUsername(username);
        return new UserIdentityAvailabilityPojo(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailabilityPojo checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userService.existsByEmail(email);
        return new UserIdentityAvailabilityPojo(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfilePojo getUserProfile(@PathVariable(value = "username") String username) {
        return userService.getUserProfile(username);
    }

    @GetMapping("/users/{username}/polls")
    public PagedResponsePojo<PollResponsePojo> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                                 @CurrentUser UserDetailService currentUser,
                                                                 @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsCreatedBy(username, currentUser, page, size);
    }

    @GetMapping("/users/{username}/votes")
    public PagedResponsePojo<PollResponsePojo> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                               @CurrentUser UserDetailService currentUser,
                                                               @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                               @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsVotedBy(username, currentUser, page, size);
    }

}

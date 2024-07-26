package np.com.softwarica.castyourvote.service.interfaces;

import np.com.softwarica.castyourvote.entity.User;
import np.com.softwarica.castyourvote.pojo.SignUpRequestPojo;
import np.com.softwarica.castyourvote.pojo.UserProfilePojo;

public interface IUserService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    public UserProfilePojo getUserProfile(String username);
    User registerUser(SignUpRequestPojo signUpRequest) throws Exception;
}

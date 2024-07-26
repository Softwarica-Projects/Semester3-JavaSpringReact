package np.com.softwarica.castyourvote.service.implementation;

import np.com.softwarica.castyourvote.core.enums.RoleName;
import np.com.softwarica.castyourvote.core.exception.AppException;
import np.com.softwarica.castyourvote.core.exception.ResourceNotFoundException;
import np.com.softwarica.castyourvote.entity.Role;
import np.com.softwarica.castyourvote.entity.User;
import np.com.softwarica.castyourvote.pojo.SignUpRequestPojo;
import np.com.softwarica.castyourvote.pojo.UserProfilePojo;
import np.com.softwarica.castyourvote.repository.IPollRepository;
import np.com.softwarica.castyourvote.repository.IRoleRepository;
import np.com.softwarica.castyourvote.repository.IUserRepository;
import np.com.softwarica.castyourvote.repository.IVoteRepository;
import np.com.softwarica.castyourvote.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IVoteRepository voteRepository;
    private final IPollRepository pollRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserProfilePojo getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());
        return new UserProfilePojo(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);
    }

    @Override
    public Role GetUserRole(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return user.getRoles().stream().findFirst().orElseThrow();
    }

    @Override
    public User registerUser(SignUpRequestPojo signUpRequest) throws Exception {
        if (existsByUsername(signUpRequest.getUsername())) {
            throw new Exception("Username is already taken!");
        }

        if (existsByEmail(signUpRequest.getEmail())) {
            throw new Exception("Email Address already in use!");
        }

        // Creating user's account
        var user = new User(null, signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), new HashSet<Role>());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        return userRepository.save(user);
    }
}

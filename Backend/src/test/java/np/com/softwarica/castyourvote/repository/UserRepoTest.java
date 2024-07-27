package np.com.softwarica.castyourvote.repository;

import np.com.softwarica.castyourvote.core.enums.RoleName;
import np.com.softwarica.castyourvote.core.exception.AppException;
import np.com.softwarica.castyourvote.entity.Choice;
import np.com.softwarica.castyourvote.entity.Poll;
import np.com.softwarica.castyourvote.entity.Role;
import np.com.softwarica.castyourvote.entity.User;
import np.com.softwarica.castyourvote.service.interfaces.IUserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepoTest {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUser() {
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        var user = User.builder()
                .email("rishan@gmail.com")
                .name("Rishan Shrestha")
                .password("Rishan@123")
                .username("rishan")
                .roles(Collections.singleton(userRole)).build();
        userRepository.save(user);
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void getUserTest() {
        var user = userRepository.findById(1L).get();
        Assertions.assertThat(user.getId()).isEqualTo(1);
    }

    //
    @Test
    @Order(3)
    public void fetchAll() {
        var userList = userRepository.findAll();
        Assertions.assertThat(userList.size()).isGreaterThan(0);
    }

    //
    @Test
    @Order(4)
    @Rollback(value = false)
    public void Update() {
        var user = userRepository.findById(1L).get();
        var newName = "Rishan-Sht";
        user.setName(newName);
        var updatedPoll = userRepository.save(user);
        Assertions.assertThat(updatedPoll.getName()).isEqualTo(newName);
    }

    //
    @Test
    @Order(5)
    @Rollback(value = false)
    public void Delete() {
        var user = userRepository.findById(1L).get();
        userRepository.delete(user);
        var deletedUser = userRepository.findById(1L);
        Assertions.assertThat(deletedUser).isNull();
    }
}

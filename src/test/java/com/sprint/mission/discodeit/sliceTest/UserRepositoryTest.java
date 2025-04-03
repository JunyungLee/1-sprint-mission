package com.sprint.mission.discodeit.sliceTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
public class UserRepositoryTest {
  @Autowired
  private UserRepository userRepository;

  @Test
  void findByUsernameSuccess() {
    //given
    User user = new User("woody", "woody@naver.com", "1234", null);
    userRepository.save(user);

    //when
    Optional<User> findUser = userRepository.findByUsername("woody");

    //then
    assertThat(findUser).isPresent();
    assertThat(findUser.get().getUsername()).isEqualTo("woody");
  }

  @Test
  void findByUsernameFailed() {
    Optional<User> findUser = userRepository.findByUsername("rex");

    assertThat(findUser).isEmpty();
  }
}

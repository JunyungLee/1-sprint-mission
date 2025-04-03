package com.sprint.mission.discodeit.sliceTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class MessageRepositoryTest {

  @Autowired
  private MessageRepository messageRepository;

  @Test
  void findAllByChannelIdWithAuthor() {
    //given
    Channel publicChannel = new Channel(ChannelType.PUBLIC, "Codeit Channel", "Public Codeit Channel");
    Message message = new Message("Hello I'm sprinter", publicChannel, new User("woody", "woody@naver.com", "1234", null), List.of());
    messageRepository.save(message);

    PageRequest pageable = PageRequest.of(0,10);

    //when
    Slice<Message> messages = messageRepository.findAllByChannelIdWithAuthor(publicChannel.getId(), Instant.now(), pageable);

    //then
    assertThat(messages.getContent()).isNotEmpty();
  }
}

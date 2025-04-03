package com.sprint.mission.discodeit.sliceTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class ChannelRepositoryTest {
  @Autowired
  private ChannelRepository channelRepository;

  @Test
  void findAllByTypeOrIdInTest() {
    //given
    Channel publicChannel = new Channel(ChannelType.PUBLIC, "Codeit Channel", "Public Codeit Channel");
    Channel privateChannel = new Channel(ChannelType.PRIVATE, "private Channel", "private Channel");

    channelRepository.saveAll(List.of(publicChannel, privateChannel));

    List<UUID> channelIds = List.of(publicChannel.getId());

    //when
    List<Channel> channels = channelRepository.findAllByTypeOrIdIn(ChannelType.PUBLIC, channelIds);

    //then
    assertThat(channels).hasSize(1);
    assertThat(channels.get(0).getType()).isEqualTo(ChannelType.PUBLIC);

  }


}

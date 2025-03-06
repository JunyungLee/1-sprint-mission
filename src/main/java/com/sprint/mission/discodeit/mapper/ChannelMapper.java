package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChannelMapper {

  private final MessageRepository messageRepository;
  private final ReadStatusRepository readStatusRepository;
  private final UserMapper userMapper;


  public ChannelDto toDto(Channel channel) {
    ChannelDto channelDto = new ChannelDto();
    channelDto.setId(channel.getId());
    channelDto.setType(channel.getType());
    channelDto.setName(channel.getName());
    channelDto.setDescription(channel.getDescription());
    return channelDto;
  }

}

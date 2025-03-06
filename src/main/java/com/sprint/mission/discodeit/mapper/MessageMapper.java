package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageMapper {

  private final BinaryContentMapper binaryContentMapper;
  private UserMapper userMapper;

  public MessageDto toDto(Message message) {
    MessageDto messageDto = new MessageDto();
    messageDto.setId(message.getId());
    messageDto.setContent(message.getContent());
    return messageDto;
  }

}

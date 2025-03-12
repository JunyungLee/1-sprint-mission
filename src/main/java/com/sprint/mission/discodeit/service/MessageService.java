package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.entity.Message;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {

  MessageDto createMessage(MessageCreateRequest request
      , List<BinaryContentDto> binaryContentCreateRequests);

  Message getMessageById(UUID id);

  List<Message> getAllMessages();

  PageResponse<MessageDto> findAllByChannelId(UUID channelID, Instant cursor, int size);

  MessageDto updateMessage(UUID id, MessageUpdateRequest request);

  void deleteMessage(UUID id);

  void deleteByChannelId(UUID channelID);

  BinaryContentDto saveAttachment(MultipartFile multipartFile);
}

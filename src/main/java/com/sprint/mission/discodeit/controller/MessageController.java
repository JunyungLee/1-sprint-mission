package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.MessageApi;
import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.service.MessageService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController implements MessageApi {

  private final MessageService messageService;

  @Override
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<MessageDto> createMessage(
      @RequestPart("messageCreateRequest") MessageCreateRequest messageCreateRequest,
      @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments) {

    List<BinaryContentDto> attachmentRequests = attachments != null
        ? attachments.stream()
        .map(this::saveAttachment)
        .collect(Collectors.toList())
        : List.of();

    MessageDto createdMessage = messageService.createMessage(messageCreateRequest,
        attachmentRequests);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
  }


  @Override
  @PatchMapping("/{messageId}")
  public ResponseEntity<MessageDto> updateMessage(@PathVariable("messageId") UUID messageId,
      @RequestBody MessageUpdateRequest request) {
    MessageDto updatedMessage = messageService.updateMessage(messageId, request);
    return ResponseEntity.status(HttpStatus.OK).body(updatedMessage);
  }

  @Override
  @DeleteMapping("/{messageId}")
  public ResponseEntity<Void> deleteMessage(@PathVariable UUID messageId) {
    messageService.deleteMessage(messageId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }


  @Override
  @GetMapping
  public ResponseEntity<PageResponse<MessageDto>> findAllByChannelId(
      @RequestParam("channelId") UUID channelId,
      @RequestParam(value = "cursor", required = false) Instant cursor,
      @RequestParam(value = "size", defaultValue = "50") int size) {
    PageResponse<MessageDto> response = messageService.findAllByChannelId(channelId, cursor, size);
    return ResponseEntity.ok(response);
  }

  private BinaryContentDto saveAttachment(MultipartFile multipartFile) {
    return messageService.saveAttachment(multipartFile);
  }
}

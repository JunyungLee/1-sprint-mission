package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.ReadStatusApi;
import com.sprint.mission.discodeit.dto.readStatus.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readStatus.ReadStatusDto;
import com.sprint.mission.discodeit.dto.readStatus.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/readStatuses")
public class ReadStatusController implements ReadStatusApi {

  private final ReadStatusService readStatusService;

  @Override
  @PostMapping
  public ResponseEntity<ReadStatusDto> createReadStatus(
      @RequestBody ReadStatusCreateRequest request) {
    ReadStatusDto readStatus = readStatusService.create(request);
    return ResponseEntity.ok(readStatus);
  }

  @Override
  @PatchMapping("/{readStatusId}")
  public ResponseEntity<ReadStatusDto> updateReadStatus(@PathVariable UUID readStatusId,
      @RequestBody ReadStatusUpdateRequest request) {
    ReadStatusDto updatedReadStatus = readStatusService.update(readStatusId, request);
    return ResponseEntity.ok(updatedReadStatus);
  }

  @Override
  @GetMapping
  public ResponseEntity<List<ReadStatusDto>> findAllByUserId(@RequestParam("userId") UUID userId) {
    List<ReadStatusDto> readStatuses = readStatusService.findAllByUserId(userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(readStatuses);

  }
}

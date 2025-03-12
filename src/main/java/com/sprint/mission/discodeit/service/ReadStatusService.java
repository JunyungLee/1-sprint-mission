package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.dto.readStatus.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readStatus.ReadStatusDto;
import com.sprint.mission.discodeit.dto.readStatus.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.status.ReadStatus;
import java.util.List;
import java.util.UUID;

public interface ReadStatusService {

  ReadStatusDto create(ReadStatusCreateRequest request);

  ReadStatus find(UUID id);

  List<ReadStatusDto> findAllByUserId(UUID userId);

  ReadStatusDto update(UUID readStatusId, ReadStatusUpdateRequest request);

  List<ReadStatus> findAllByChannelId(UUID userId);

  void delete(UUID id);

  void deleteByChannelId(UUID id);
}

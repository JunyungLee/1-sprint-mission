package com.sprint.mission.discodeit.service.featureBasicService;

import com.sprint.mission.discodeit.dto.readStatus.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readStatus.ReadStatusDto;
import com.sprint.mission.discodeit.dto.readStatus.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.status.ReadStatus;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {

  private final ReadStatusRepository readStatusRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;
  private final ReadStatusMapper readStatusMapper;

  @Override
  @Transactional
  public ReadStatusDto create(ReadStatusCreateRequest request) {
    Channel channel = channelRepository.findById(request.getChannelId())
        .orElseThrow(() -> new NoSuchElementException("No channel found"));

    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new NoSuchElementException("No user found"));

    if (readStatusRepository.existsByUserIdAndChannelId(request.getChannelId(),
        request.getUserId())) {
      throw new NoSuchElementException("user already has member of channel");
    }

    Instant now = request.getLastReadAt();
    ReadStatus readStatus = new ReadStatus(user, channel, now);

    return readStatusMapper.toDto(readStatusRepository.save(readStatus));
  }

  @Override
  public ReadStatus find(UUID id) {
    return readStatusRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("readStatus not found"));
  }

  @Override
  public List<ReadStatusDto> findAllByUserId(UUID userId) {
    return readStatusRepository.findAllByUserId(userId)
        .stream().map(readStatusMapper::toDto).toList();
  }

  @Override
  @Transactional
  public ReadStatusDto update(UUID readStatusId, ReadStatusUpdateRequest request) {
    ReadStatus readStatus = readStatusRepository.findById(readStatusId)
        .orElseThrow(() -> new NoSuchElementException("ReadStatus id not found"));
    readStatus.update(request.getNewLastReadAt());
    return readStatusMapper.toDto(readStatus);
  }

  @Override
  public List<ReadStatus> findAllByChannelId(UUID channelId) {
    return readStatusRepository.findAllByChannelId(channelId);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    if (!readStatusRepository.existsById(id)) {
      throw new NoSuchElementException("readStatus not found");
    }
    readStatusRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteByChannelId(UUID id) {
    readStatusRepository.deleteByChannelId(id);
  }
}

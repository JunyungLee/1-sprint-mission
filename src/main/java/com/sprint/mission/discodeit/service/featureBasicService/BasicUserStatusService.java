package com.sprint.mission.discodeit.service.featureBasicService;

import com.sprint.mission.discodeit.dto.user.userStatus.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.user.userStatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.status.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {

  private final UserStatusRepository userStatusRepository;
  private final UserRepository userRepository;


  @Override
  @Transactional
  public UserStatus create(UserStatusCreateRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new NoSuchElementException("user not found"));

    Optional<UserStatus> existingStatus = userStatusRepository.findByUserId(request.getUserId());
    if (existingStatus.isPresent()) {
      throw new IllegalArgumentException("UserStatus already exists");
    }
    UserStatus userStatus = new UserStatus(user, request.getCreatedAt());
    System.out.println("userStatus 생성:" + userStatus.getId());
    return userStatusRepository.save(userStatus);
  }

  @Override
  public UserStatus find(UUID id) {
    return userStatusRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User status not found"));
  }

  @Override
  public List<UserStatus> findAll() {
    return userStatusRepository.findAll();
  }

  @Override
  @Transactional
  public void update(UUID userStatusId, UserStatusUpdateRequest request) {
    UserStatus userStatus = userStatusRepository.findById(userStatusId)
        .orElseThrow(() -> new NoSuchElementException("UserStatus not found"));
    userStatus.update(request.getNewLastActiveAt());
    userStatusRepository.save(userStatus);
  }

  @Override
  @Transactional
  public UserStatus updateByUserId(UUID userId, UserStatusUpdateRequest request) {
    UserStatus userStatus = userStatusRepository.findByUserId(userId)
        .orElseThrow(() -> new NoSuchElementException("UserStatus not found"));
    userStatus.update(request.getNewLastActiveAt());
    return userStatusRepository.save(userStatus);
  }


  @Override
  @Transactional
  public void deleteByUserId(UUID userId) {
    if (!userStatusRepository.existsById(userId)) {
      throw new NoSuchElementException("UserStatus not found");
    }
    userStatusRepository.deleteByUserId(userId);
  }
}

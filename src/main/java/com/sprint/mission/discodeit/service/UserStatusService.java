package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.userStatus.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.user.userStatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.status.UserStatus;
import java.util.List;
import java.util.UUID;

public interface UserStatusService {

  UserStatus create(UserStatusCreateRequest request);

  UserStatus find(UUID id);

  List<UserStatus> findAll();

  void update(UUID userStatusId, UserStatusUpdateRequest request);

  UserStatus updateByUserId(UUID userId, UserStatusUpdateRequest request);

  void deleteByUserId(UUID userId);
}

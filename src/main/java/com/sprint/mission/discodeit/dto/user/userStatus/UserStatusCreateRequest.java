package com.sprint.mission.discodeit.dto.user.userStatus;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatusCreateRequest {

  private UUID userId;
  private Instant createdAt;

  public UserStatusCreateRequest(UUID userId, Instant createdAt) {
    this.userId = userId;
    this.createdAt = createdAt;
  }
}

package com.sprint.mission.discodeit.dto.user.userStatus;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserStatusDto {

  UUID id;
  UUID userId;
  Instant lastActiveAt;

}

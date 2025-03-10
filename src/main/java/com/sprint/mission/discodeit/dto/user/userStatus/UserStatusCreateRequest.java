package com.sprint.mission.discodeit.dto.user.userStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserStatusCreateRequest(
    UUID userId,
    Instant lastActiveAt
) {

}

package com.sprint.mission.discodeit.dto.user.userStatus;

import java.time.Instant;
import java.time.LocalDateTime;

public record UserStatusUpdateRequest(
    LocalDateTime newLastActiveAt
) {

}

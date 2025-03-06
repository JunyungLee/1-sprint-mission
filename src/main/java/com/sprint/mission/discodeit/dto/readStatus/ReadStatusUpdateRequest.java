package com.sprint.mission.discodeit.dto.readStatus;

import java.time.Instant;
import java.time.LocalDateTime;

public record ReadStatusUpdateRequest(
    LocalDateTime newLastReadAt
) {

}

package com.sprint.mission.discodeit.dto.user;

import java.time.Instant;
import java.util.UUID;

public record UserDto(UUID id, Instant createdAt, Instant updatedAt, String username,
                      String userEmail, UUID profileId, Boolean online) {

}

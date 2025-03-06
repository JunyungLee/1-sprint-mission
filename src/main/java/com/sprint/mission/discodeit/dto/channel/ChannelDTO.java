package com.sprint.mission.discodeit.dto.channel;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ChannelDto(
    UUID id, ChannelType type,
    String name, String description,
    List<UUID> participantIds,
    LocalDateTime lastMessageAt
) {

}

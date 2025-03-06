package com.sprint.mission.discodeit.dto.readStatus;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReadStatusDto {

  UUID id;
  UUID userId;
  UUID channelId;
  Instant lastReadAt;

}

package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.readStatus.ReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadStatusMapper {

  public ReadStatusDto toDto(ReadStatus readStatus) {
    ReadStatusDto readStatusDto = new ReadStatusDto();
    readStatusDto.setId(readStatus.getId());
    readStatusDto.setLastReadAt(Instant.from(readStatus.getLastReadAt()));
    readStatusDto.setUserId(readStatus.getUser().getId());
    readStatusDto.setChannelId(readStatus.getChannel().getId());
    return readStatusDto;
  }

}

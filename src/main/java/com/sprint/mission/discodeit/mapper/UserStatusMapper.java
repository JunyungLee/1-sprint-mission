package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.user.userStatus.UserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStatusMapper {

  public UserStatusDto toDto(UserStatus userStatus) {
    UserStatusDto userStatusDto = new UserStatusDto();
    userStatusDto.setId(userStatus.getId());
    userStatusDto.setUserId(userStatus.getUser().getId());
    userStatusDto.setLastActiveAt(Instant.from(userStatus.getLastActiveAt()));
    return userStatusDto;
  }

}

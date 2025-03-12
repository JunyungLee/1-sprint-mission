package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.user.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  UserDto createUser(UserCreateRequest request
      , MultipartFile profile);

  UserDto getUserById(UUID id);

  List<UserDto> getAllUsers();

  UserDto updateUser(UUID userId, UserUpdateRequest request
      , MultipartFile profile);

  void deleteUser(UUID id);

  BinaryContentDto saveProfileImage(MultipartFile profileFile);
}


package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BasicUserService implements UserService {

  private final UserRepository userRepository;
  private final UserStatusRepository userStatusRepository;
  private final BinaryContentRepository binaryContentRepository;
  private final UserMapper userMapper;


  @Override
  @Transactional
  public User create(UserCreateRequest userCreateRequest,
      Optional<BinaryContentCreateRequest> optionalProfileCreateRequest) {
    String username = userCreateRequest.username();
    String email = userCreateRequest.email();

    if (userRepository.existsByEmail(email)) {
      throw new IllegalArgumentException(email + "already exists");
    }
    if (userRepository.existsByUsername(username)) {
      throw new IllegalArgumentException(username + "already exists");
    }

    //Profile Id 처리 (파일이 있을 경우)
    BinaryContent nullableProfile = optionalProfileCreateRequest
        .map(profileRequest -> {
          String fileName = profileRequest.fileName();
          String contentType = profileRequest.contentType();
          byte[] bytes = profileRequest.bytes();
          BinaryContent binaryContent = new BinaryContent(fileName, (long) bytes.length,
              contentType);
          return binaryContentRepository.save(binaryContent);
        })
        .orElse(null);

    String password = userCreateRequest.password();
    User user = new User(username, email, password, nullableProfile);
    User createdUser = userRepository.save(user);

    //userStatus 생성
    LocalDateTime now = LocalDateTime.now();
    UserStatus userStatus = new UserStatus(createdUser, now);
    userStatusRepository.save(userStatus);

    return createdUser;
  }

  @Override
  public UserDto find(UUID userId) {
    return userRepository.findById(userId)
        .map(userMapper::toDto)
        .orElseThrow(() -> new NoSuchElementException(userId + "not found"));
  }

  @Override
  public List<UserDto> findAll() {
    return userRepository.findAll()
        .stream()
        .map(userMapper::toDto)
        .toList();
  }

  @Override
  @Transactional
  public User update(UUID userId, UserUpdateRequest userUpdateRequest,
      Optional<BinaryContentCreateRequest> optionalProfileCreateRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException(userId + "not found"));

    String newUsername = userUpdateRequest.newUsername();
    String newEmail = userUpdateRequest.newEmail();
    if (userRepository.existsByEmail(newEmail)) {
      throw new IllegalArgumentException(newEmail + "already Exists");
    }
    if (userRepository.existsByUsername(newUsername)) {
      throw new IllegalArgumentException(newUsername + "already Exists");
    }
    BinaryContent nullableProfile = optionalProfileCreateRequest
        .map(profileRequest -> {
          Optional.ofNullable(user.getProfile()).ifPresent(binaryContentRepository::delete);

          String fileName = profileRequest.fileName();
          String contentType = profileRequest.contentType();
          byte[] bytes = profileRequest.bytes();
          BinaryContent binaryContent = new BinaryContent(fileName, (long) bytes.length,
              contentType);
          return binaryContentRepository.save(binaryContent);
        })
        .orElse(null);

    String newPassword = userUpdateRequest.newPassword();
    user.update(newUsername, newEmail, newPassword, nullableProfile);
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public void delete(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException(userId + "not found"));
    Optional.ofNullable(user.getProfile()).ifPresent(binaryContentRepository::delete);

    userStatusRepository.deleteByUserId(userId);
    userRepository.deleteById(userId);
  }
}

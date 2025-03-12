package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.user.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.status.UserStatus;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

  private final UserRepository userRepository;
  private final BinaryContentRepository binaryContentRepository;
  private final UserMapper userMapper;
  private final BinaryContentStorage binaryContentStorage;
  private final BinaryContentMapper binaryContentMapper;

  @Autowired
  private EntityManager entityManager;

  @Override
  @Transactional
  public UserDto createUser(UserCreateRequest request, MultipartFile profile) {
    if (request.getUsername() == null || request.getEmail() == null
        || request.getPassword() == null) {
      throw new IllegalArgumentException("사용자 정보를 올바르게 입력해야 합니다.");
    }
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email already in use");
    }
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new IllegalArgumentException("Name already in use");
    }

    BinaryContent profileImage =
        (profile != null && !profile.isEmpty()) ? saveProfile(profile) : null;

    User user = new User(request.getUsername(), request.getEmail(), request.getPassword(),
        profileImage);
    userRepository.save(user);

    Instant now = Instant.now();
    UserStatus userStatus = new UserStatus(user, now);
    user.setStatus(userStatus);

    return userMapper.toDto(user);
  }


  @Override
  @Transactional
  public UserDto getUserById(UUID id) {
    return userRepository.findById(id)
        .map(userMapper::toDto)
        .orElseThrow(() -> new IllegalArgumentException("User id : " + id + " not found"));
  }

  @Override
  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream().map(userMapper::toDto).toList();
  }


  @Override
  @Transactional
  public UserDto updateUser(UUID userId, UserUpdateRequest request, MultipartFile profile) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("User not found"));

    BinaryContent oldProfile = user.getProfile();

    if (profile != null && !profile.isEmpty()) {
      if (oldProfile == null || !oldProfile.getFileName().equals(profile.getOriginalFilename())) {
        if (oldProfile != null) {
          binaryContentStorage.delete(oldProfile.getId(),
              getFileExtension(oldProfile.getFileName()));
          binaryContentRepository.deleteById(oldProfile.getId());
        }

        BinaryContent newProfile = saveProfile(profile);
        user.setProfile(newProfile);
      }
    }

    user.update(request.getNewUsername(), request.getNewEmail(), request.getNewPassword());
    user.getStatus().update(Instant.now());

    return userMapper.toDto(user);
  }


  private String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
  }

  @Override
  @Transactional
  public void deleteUser(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("User not found"));

    if (user.getProfile() != null) {
      UUID profileId = user.getProfile().getId();
      String extension = getFileExtension(user.getProfile().getFileName());
      binaryContentStorage.delete(profileId, extension);
      binaryContentRepository.delete(user.getProfile());
    }

    userRepository.delete(user);
  }


  private BinaryContent saveProfile(MultipartFile profileFile) {
    if (profileFile == null || profileFile.isEmpty()) {
      throw new NoSuchElementException("Profile file is null or empty");
    }

    BinaryContent binaryContent = new BinaryContent(
        profileFile.getOriginalFilename(),
        profileFile.getSize(),
        profileFile.getContentType()
    );

    BinaryContent savedContent = binaryContentRepository.save(binaryContent);
    String extension = getFileExtension(profileFile.getOriginalFilename());

    try {
      Path filePath = binaryContentStorage.put(savedContent.getId(), profileFile.getBytes(),
          extension);
      savedContent.setFilePath(filePath.toString());
    } catch (IOException e) {
      throw new RuntimeException("Failed to save profile file", e);
    }

    return savedContent;
  }

  @Override
  @Transactional
  public BinaryContentDto saveProfileImage(MultipartFile profileFile) {
    if (profileFile == null || profileFile.isEmpty()) {
      throw new NoSuchElementException("multipartFile is null or empty");
    }
    BinaryContent binaryContent = new BinaryContent(
        profileFile.getOriginalFilename(),
        profileFile.getSize(),
        profileFile.getContentType()
    );
    BinaryContent savedContent = binaryContentRepository.save(binaryContent);

    String extension = getFileExtension(profileFile.getOriginalFilename());

    try {
      Path filePath = binaryContentStorage.put(savedContent.getId(), profileFile.getBytes(),
          extension);
      binaryContent.setFilePath(filePath.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return binaryContentMapper.toDto(savedContent);
  }
}

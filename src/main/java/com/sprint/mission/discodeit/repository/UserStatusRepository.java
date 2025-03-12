package com.sprint.mission.discodeit.repository;


import com.sprint.mission.discodeit.entity.status.UserStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, UUID> {

  void deleteByUserId(UUID id);

  Optional<UserStatus> findByUserId(UUID userId);
}

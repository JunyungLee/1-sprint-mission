package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  //사용자 이름으로 조회
  Optional<User> findByUsername(String username);

  //이메일로 사용자 조회
  Optional<User> findByEmail(String email);

  //이메일 존재 여부
  boolean existsByEmail(String email);

  //사용자 이름 존재 여부
  boolean existsByUsername(String username);

}

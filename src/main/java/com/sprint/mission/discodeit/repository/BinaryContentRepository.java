package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BinaryContentRepository extends JpaRepository<BinaryContent, UUID> {

  //여러 Id로 BinaryContent를 찾는 쿼리 메소드
  List<BinaryContent> findAllByIdIn(List<UUID> ids);
}

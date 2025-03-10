package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface BinaryContentService {

  UUID saveContent(String fileName, Long size, String contentType, byte[] data);

  ResponseEntity<?> downloadContent(BinaryContentDto dto);

  InputStream loadContent(UUID id);
}

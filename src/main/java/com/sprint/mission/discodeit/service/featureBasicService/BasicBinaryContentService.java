package com.sprint.mission.discodeit.service.featureBasicService;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BasicBinaryContentService implements BinaryContentService {

  private final BinaryContentStorage binaryContentStorage;
  private final BinaryContentRepository binaryContentRepository;


  @Transactional
  @Override
  public UUID saveContent(String fileName, Long size, String contentType, byte[] data) {
    BinaryContent binaryContent = new BinaryContent(fileName, size, contentType);
    BinaryContent savedContent = binaryContentRepository.save(binaryContent);
    UUID id = savedContent.getId();
    binaryContentStorage.put(id, data);
    return id;
  }

  @Transactional(readOnly = true)
  @Override
  public ResponseEntity<?> downloadContent(BinaryContentDto dto) {
    if (!binaryContentRepository.existsById(dto.getId())) {
      throw new NoSuchElementException("BinaryContent not found" + dto.getId());
    }
    return binaryContentStorage.download(dto);
  }

  @Override
  public InputStream loadContent(UUID id) {
    if (!binaryContentRepository.existsById(id)) {
      throw new NoSuchElementException("BinaryContent not found" + id);
    }
    return binaryContentStorage.get(id);
  }
}

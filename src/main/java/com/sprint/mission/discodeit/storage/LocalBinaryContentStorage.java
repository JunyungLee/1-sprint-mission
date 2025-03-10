package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LocalBinaryContentStorage implements BinaryContentStorage {

  @Value("${discodeit.storage.local.root-path}")
  private String rootPath;
  private Path root;

  @PostConstruct
  public void init() {
    this.root = Paths.get(rootPath);
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("Failed to initialize storage", e);
    }
  }

  @Override
  public UUID put(UUID id, byte[] data) {
    try {
      Path filePath = resolvePath(id);
      Files.write(filePath, data);
      return id;
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file", e);
    }
  }

  @Override
  public InputStream get(UUID id) {
    try {
      Path filePath = resolvePath(id);
      return Files.newInputStream(filePath);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file", e);
    }
  }

  @Override
  public ResponseEntity<Resource> download(BinaryContentDto dto) {
    Path filePath = resolvePath(dto.getId());

    // 파일이 존재하는지 확인
    if (Files.exists(filePath)) {
      Resource resource = new FileSystemResource(filePath);
      return ResponseEntity.ok().body(resource);
    } else {
      return ResponseEntity.notFound().build();  // 파일이 존재하지 않으면 404 반환
    }
  }

  private Path resolvePath(UUID id) {
    return root.resolve(id.toString()).toAbsolutePath();
  }
}

package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.BinaryContentApi;
import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/binaryContents")
@RequiredArgsConstructor
public class BinaryContentController implements BinaryContentApi {

  private final BinaryContentService binaryContentService;
  private final BinaryContentStorage binaryContentStorage;
  private final BinaryContentMapper binaryContentMapper;

  @Override
  @GetMapping("/{binaryContentId}")
  public ResponseEntity<BinaryContent> find(@PathVariable("binaryContentId") UUID binaryContentId) {
    BinaryContent binaryContent = binaryContentService.find(binaryContentId);
    return ResponseEntity.status(HttpStatus.OK).body(binaryContent);
  }

  @Override
  public ResponseEntity<List<BinaryContent>> findAllByIdIn(List<UUID> binaryContentIds) {
    return ResponseEntity.ok(binaryContentService.findAllByIdIn(binaryContentIds));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteBinaryContent(@PathVariable UUID id) {
    binaryContentService.delete(id);
    return ResponseEntity.ok("delete success");
  }

  @GetMapping("/{binaryContentId}/download")
  public ResponseEntity<?> downloadBinaryContent(@PathVariable UUID binaryContentId) {
    BinaryContent binaryContent = binaryContentService.find(binaryContentId);
    if (binaryContent == null) {
      return ResponseEntity.notFound().build();
    }
    System.out.println("🔍 [DEBUG] 다운로드 요청 파일: " + binaryContent.getFileName());
    String extension = getFileExtension(binaryContent.getFileName());
    BinaryContentDto binaryContentDto = binaryContentMapper.toDto(binaryContent);
    return binaryContentStorage.download(binaryContentDto, extension);
  }


  private String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
  }
}

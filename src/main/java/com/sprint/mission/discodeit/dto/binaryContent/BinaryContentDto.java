package com.sprint.mission.discodeit.dto.binaryContent;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BinaryContentDto {

  UUID id;
  String fileName;
  Long size;
  String contentType;

  public BinaryContentDto(UUID binaryContentId) {
    this.id = binaryContentId;
  }
}

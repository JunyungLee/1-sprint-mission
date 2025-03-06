package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BinaryContentMapper {

  public BinaryContentDto toDto(BinaryContent binaryContent) {
    BinaryContentDto binaryContentDto = new BinaryContentDto();
    binaryContentDto.setId(binaryContent.getId());
    binaryContentDto.setContentType(binaryContent.getContentType());
    binaryContentDto.setFileName(binaryContent.getFileName());
    binaryContentDto.setSize(binaryContent.getSize());
    return binaryContentDto;
  }

}

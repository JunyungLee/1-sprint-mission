package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface BinaryContentStorage {

  //바이너리 데이터 저장 메소드
  UUID put(UUID id, byte[] data);

  //바이너리 데이터를 읽는 메소드
  InputStream get(UUID id);

  //다운로드 기능 메소드
  ResponseEntity<?> download(BinaryContentDto dto);

}

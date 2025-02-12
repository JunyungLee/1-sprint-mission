package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final UUID id ;
    private final Instant createdAt;
    private Instant updatedAt;

    //생성자
    public BaseEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
    }
    //필드 수정 -> update 메소드
    public void update(long updatedAt) {
        this.updatedAt = Instant.now();
    }
}

package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "read_Status")
public class ReadStatus extends BaseEntity {

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "channel_id", nullable = false)
  private Channel channel;
  @Column(name = "last_read_at")
  private LocalDateTime lastReadAt;

  public ReadStatus(User user, Channel channel, LocalDateTime lastReadAt) {
    this.user = user;
    this.channel = channel;
    this.lastReadAt = lastReadAt;
  }

  public void update(LocalDateTime newLastReadAt) {
    boolean anyValueUpdated = false;
    if (newLastReadAt != null && !newLastReadAt.equals(this.lastReadAt)) {
      this.lastReadAt = newLastReadAt;
      anyValueUpdated = true;
    }
  }
}

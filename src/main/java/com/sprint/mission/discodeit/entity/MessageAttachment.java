package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "message_attachments")
public class MessageAttachment extends BaseEntity {

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "message_id", nullable = false)
  private Message message;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "attachment_id", nullable = false)
  private BinaryContent attachment;

}

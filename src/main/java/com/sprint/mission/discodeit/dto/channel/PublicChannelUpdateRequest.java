package com.sprint.mission.discodeit.dto.channel;

import lombok.Getter;

@Getter
public class PublicChannelUpdateRequest {

  private String newName;
  private String NewDescription;

  public PublicChannelUpdateRequest(String newName, String newDescription) {
    this.newName = newName;
    NewDescription = newDescription;
  }
}

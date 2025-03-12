package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.PublicChannelUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface ChannelService {

  ChannelDto createPublicChannel(PublicChannelCreateRequest request);

  ChannelDto createPrivateChannel(PrivateChannelCreateRequest request);

  ChannelDto getChannelById(UUID id);

  List<ChannelDto> findAllByUserId(UUID userid);

  ChannelDto updateChannel(UUID channelId, PublicChannelUpdateRequest request);

  void deleteChannel(UUID id);
}

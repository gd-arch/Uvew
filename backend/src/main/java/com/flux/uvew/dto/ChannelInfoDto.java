package com.flux.uvew.dto;

import com.flux.uvew.model.Video;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelInfoDto {
    private String channelId;
    private String channelName;
    private Integer totalSubscribers;
    private List<Video> videos;
    private String ownerId;
}

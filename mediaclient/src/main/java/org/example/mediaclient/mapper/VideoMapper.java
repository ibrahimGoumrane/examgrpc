
package org.example.mediaclient.mapper;

import org.example.lab.Video;
import org.example.lab.UploadVideoRequest;
import org.example.lab.Creator;
import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.dto.CreatorDto;
import org.example.mediaclient.dto.UploadVideoRequestDto;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public static VideoDto fromVideoProtoToVideoDto(Video video) {
        if (video == null) {
            return null;
        }

        VideoDto videoDto = new VideoDto();
        videoDto.setId(video.getId());
        videoDto.setTitle(video.getTitle());
        videoDto.setDescription(video.getDescription());
        videoDto.setUrl(video.getUrl());
        videoDto.setDurationSeconds(video.getDurationSeconds());

        if (video.hasCreator()) {
            CreatorDto creator = new CreatorDto();
            creator.setId(video.getCreator().getId());
            creator.setName(video.getCreator().getName());
            creator.setEmail(video.getCreator().getEmail());

            videoDto.setCreator(creator);
        }

        return videoDto;
    }

    public static UploadVideoRequest toUploadVideoProto(UploadVideoRequestDto dto) {
        if (dto == null)
            return null;

        UploadVideoRequest.Builder builder = UploadVideoRequest.newBuilder()
                .setTitle(dto.getTitle())
                .setDescription(dto.getDescription())
                .setUrl(dto.getUrl())
                .setDurationSeconds(dto.getDurationSeconds());

        if (dto.getCreator() != null) {
            Creator creator = Creator.newBuilder()
                    .setId(dto.getCreator().getId())
                    .setName(dto.getCreator().getName())
                    .setEmail(dto.getCreator().getEmail())
                    .build();
            builder.setCreator(creator);
        }

        return builder.build();
    }
}

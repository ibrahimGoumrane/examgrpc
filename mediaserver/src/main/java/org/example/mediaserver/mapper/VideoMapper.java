package org.example.mediaserver.mapper;

import org.example.lab.Video;
import org.example.lab.Creator;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public static Video toProto(org.example.mediaserver.dao.Video video) {
        if (video == null)
            return null;

        Video.Builder builder = Video.newBuilder()
                .setId(video.getId())
                .setTitle(video.getTitle())
                .setDescription(video.getDescription())
                .setUrl(video.getUrl())
                .setDurationSeconds(video.getDurationSeconds());

        if (video.getCreator() != null) {
            Creator creator = Creator.newBuilder()
                    .setId(video.getCreator().getId())
                    .setName(video.getCreator().getName())
                    .setEmail(video.getCreator().getEmail())
                    .build();
            builder.setCreator(creator);
        }

        return builder.build();
    }

    public static org.example.mediaserver.dao.Creator fromProtoCreator(Creator protoCreator) {
        if (protoCreator == null)
            return null;

        org.example.mediaserver.dao.Creator creator = new org.example.mediaserver.dao.Creator();
        creator.setId(protoCreator.getId());
        creator.setName(protoCreator.getName());
        creator.setEmail(protoCreator.getEmail());
        return creator;
    }
}
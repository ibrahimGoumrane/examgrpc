package org.example.mediaserver.mapper;

import org.example.lab.Creator;
import org.springframework.stereotype.Component;

@Component
public class CreatorMapper {

    public static Creator toProto(org.example.mediaserver.dao.Creator creator) {
        if (creator == null) return null;

        return Creator.newBuilder()
                .setId(creator.getId())
                .setName(creator.getName())
                .setEmail(creator.getEmail())
                .build();
    }
}

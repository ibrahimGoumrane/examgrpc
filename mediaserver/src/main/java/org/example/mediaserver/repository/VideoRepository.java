package org.example.mediaserver.repository;

import org.example.mediaserver.dao.Video;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class VideoRepository {

    private final Map<String, Video> videos = new ConcurrentHashMap<>();

    public Video save(Video video) {
        videos.put(video.getId(), video);
        return video;
    }

    public Video findById(String id) {
        return videos.get(id);
    }

    public List<Video> findByCreatorId(String creatorId) {
        return videos.values().stream()
                .filter(video -> video.getCreator() != null &&
                        creatorId.equals(video.getCreator().getId()))
                .collect(Collectors.toList());
    }

    public List<Video> findAll() {
        return new ArrayList<>(videos.values());
    }
}

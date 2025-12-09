package org.example.mediaserver.service;

import org.example.mediaserver.repository.CreatorRepository;
import org.example.mediaserver.repository.VideoRepository;
import org.example.mediaserver.dao.Video;
import org.example.mediaserver.dao.Creator;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final CreatorRepository creatorRepository;

    public VideoService(VideoRepository videoRepository, CreatorRepository creatorRepository) {
        this.videoRepository = videoRepository;
        this.creatorRepository = creatorRepository;
    }

    public Video uploadVideo(String title, String description, String url,
            int durationSeconds, Creator creator) {

        Video video = new Video();
        video.setId(UUID.randomUUID().toString());
        video.setTitle(title);
        video.setDescription(description);
        video.setUrl(url);
        video.setDurationSeconds(durationSeconds);
        video.setCreator(creator);

        // Save the creator if it doesn't exist
        creatorRepository.save(creator);

        return videoRepository.save(video);
    }

    public Video getVideoById(String id) {
        return videoRepository.findById(id);
    }
}
package org.example.mediaserver.service;

import org.example.mediaserver.repository.CreatorRepository;
import org.example.mediaserver.repository.VideoRepository;
import org.example.mediaserver.dao.Creator;
import org.example.mediaserver.dao.Video;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CreatorService {

    private final CreatorRepository creatorRepository;
    private final VideoRepository videoRepository;

    public CreatorService(CreatorRepository creatorRepository, VideoRepository videoRepository) {
        this.creatorRepository = creatorRepository;
        this.videoRepository = videoRepository;
    }

    public Creator getCreatorById(String id) {
        return creatorRepository.findById(id);
    }

    public List<Video> getCreatorVideos(String creatorId) {
        return videoRepository.findByCreatorId(creatorId);
    }
}
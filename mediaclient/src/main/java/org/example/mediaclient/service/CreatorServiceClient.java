package org.example.mediaclient.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.lab.*;
import org.example.mediaclient.dto.CreatorDto;
import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.mapper.CreatorMapper;
import org.example.mediaclient.mapper.VideoMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreatorServiceClient {

    @GrpcClient("mediaserver")
    CreatorServiceGrpc.CreatorServiceBlockingStub creatorStub;

    public CreatorDto getCreator(String id) {
        CreatorIdRequest request = CreatorIdRequest.newBuilder()
                .setId(id)
                .build();
        Creator creator = creatorStub.getCreator(request);
        return CreatorMapper.fromCreatorProtoToCreatorDto(creator);
    }

    public List<VideoDto> getCreatorVideos(String creatorId) {
        CreatorIdRequest request = CreatorIdRequest.newBuilder()
                .setId(creatorId)
                .build();
        VideoStream videoStream = creatorStub.getCreatorVideos(request);

        return videoStream.getVideosList().stream()
                .map(VideoMapper::fromVideoProtoToVideoDto)
                .collect(Collectors.toList());
    }
}

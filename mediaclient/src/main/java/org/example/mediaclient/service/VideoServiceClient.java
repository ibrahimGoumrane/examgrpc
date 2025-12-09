package org.example.mediaclient.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.lab.*;
import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.mapper.VideoMapper;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceClient {

    @GrpcClient("mediaserver")
    VideoServiceGrpc.VideoServiceBlockingStub videoStub;

    public VideoDto uploadVideo(UploadVideoRequest videoRequest) {
        Video video = videoStub.uploadVideo(videoRequest);
        return VideoMapper.fromVideoProtoToVideoDto(video);
    }

    public VideoDto getVideo(String id) {
        VideoIdRequest request = VideoIdRequest.newBuilder()
                .setId(id)
                .build();
        Video video = videoStub.getVideo(request);
        return VideoMapper.fromVideoProtoToVideoDto(video);
    }
}
package org.example.mediaserver.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.lab.*;
import org.example.mediaserver.service.VideoService;
import org.example.mediaserver.mapper.VideoMapper;
import org.example.mediaserver.dao.Creator;

@GrpcService
public class VideoGrpcService extends VideoServiceGrpc.VideoServiceImplBase {

    private final VideoService videoService;

    public VideoGrpcService(VideoService videoService) {
        this.videoService = videoService;
    }

    @Override
    public void uploadVideo(UploadVideoRequest request, StreamObserver<Video> responseObserver) {
        try {
            Creator creator = VideoMapper.fromProtoCreator(request.getCreator());

            org.example.mediaserver.dao.Video video = videoService.uploadVideo(
                    request.getTitle(),
                    request.getDescription(),
                    request.getUrl(),
                    request.getDurationSeconds(),
                    creator);

            Video response = VideoMapper.toProto(video);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getVideo(VideoIdRequest request, StreamObserver<Video> responseObserver) {
        try {
            org.example.mediaserver.dao.Video video = videoService.getVideoById(request.getId());

            if (video == null) {
                responseObserver.onError(new RuntimeException("Video not found"));
                return;
            }

            Video response = VideoMapper.toProto(video);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
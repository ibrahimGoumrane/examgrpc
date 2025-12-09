package org.example.mediaserver.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.lab.*;
import org.example.mediaserver.service.CreatorService;
import org.example.mediaserver.mapper.CreatorMapper;
import org.example.mediaserver.mapper.VideoMapper;
import org.example.mediaserver.dao.Video;
import java.util.List;

@GrpcService
public class CreatorGrpcService extends CreatorServiceGrpc.CreatorServiceImplBase {

    private final CreatorService creatorService;

    public CreatorGrpcService(CreatorService creatorService) {
        this.creatorService = creatorService;
    }

    @Override
    public void getCreator(CreatorIdRequest request, StreamObserver<Creator> responseObserver) {
        try {
            org.example.mediaserver.dao.Creator creator = creatorService.getCreatorById(request.getId());

            if (creator == null) {
                responseObserver.onError(new RuntimeException("Creator not found"));
                return;
            }

            Creator response = CreatorMapper.toProto(creator);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCreatorVideos(CreatorIdRequest request, StreamObserver<VideoStream> responseObserver) {
        try {
            List<Video> videos = creatorService.getCreatorVideos(request.getId());

            VideoStream.Builder streamBuilder = VideoStream.newBuilder();
            for (Video video : videos) {
                streamBuilder.addVideos(VideoMapper.toProto(video));
            }

            responseObserver.onNext(streamBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
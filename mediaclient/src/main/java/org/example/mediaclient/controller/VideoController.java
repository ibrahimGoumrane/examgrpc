package org.example.mediaclient.controller;

import org.example.lab.UploadVideoRequest;
import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.dto.UploadVideoRequestDto;
import org.example.mediaclient.service.VideoServiceClient;
import org.example.mediaclient.mapper.VideoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/videos")
@Tag(name = "Video", description = "Video management APIs")
public class VideoController {

    private final VideoServiceClient videoServiceClient;
    private final VideoMapper videoMapper;

    public VideoController(VideoServiceClient videoServiceClient, VideoMapper videoMapper) {
        this.videoServiceClient = videoServiceClient;
        this.videoMapper = videoMapper;
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload a new video")
    public ResponseEntity<VideoDto> uploadVideo(@RequestBody UploadVideoRequestDto uploadRequest) {
        UploadVideoRequest protoRequest = videoMapper.toUploadVideoProto(uploadRequest);
        VideoDto video = videoServiceClient.uploadVideo(protoRequest);
        return ResponseEntity.ok(video);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get video by ID")
    public ResponseEntity<VideoDto> getVideo(@PathVariable String id) {
        VideoDto video = videoServiceClient.getVideo(id);
        return ResponseEntity.ok(video);
    }
}

package org.example.mediaclient.controller;

import org.example.mediaclient.dto.CreatorDto;
import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.service.CreatorServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/creators")
@Tag(name = "Creator", description = "Creator management APIs")
public class CreatorController {

    private final CreatorServiceClient creatorServiceClient;

    public CreatorController(CreatorServiceClient creatorServiceClient) {
        this.creatorServiceClient = creatorServiceClient;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get creator by ID")
    public ResponseEntity<CreatorDto> getCreator(@PathVariable String id) {
        CreatorDto creator = creatorServiceClient.getCreator(id);
        return ResponseEntity.ok(creator);
    }

    @GetMapping("/{id}/videos")
    @Operation(summary = "Get videos by creator ID")
    public ResponseEntity<List<VideoDto>> getCreatorVideos(@PathVariable String id) {
        List<VideoDto> videos = creatorServiceClient.getCreatorVideos(id);
        return ResponseEntity.ok(videos);
    }
}

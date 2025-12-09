package org.example.mediaclient.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDto {
    private String id;
    private String title;
    private String description;
    private String url;
    private int durationSeconds;
    private CreatorDto creator;
}

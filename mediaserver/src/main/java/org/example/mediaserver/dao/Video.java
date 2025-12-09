package org.example.mediaserver.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private String id;
    private String title;
    private String description;
    private String url;
    private int durationSeconds;
    private Creator creator;

}

package com.blinder.api.Music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicResponseDto {
    private String spotifyId;
    private String name;
    private List<String> artists;
    private String album;
    private String image;

}

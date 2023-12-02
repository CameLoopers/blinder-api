package com.blinder.api.Music.controller;

import com.blinder.api.Music.dto.MusicResponseDto;
import com.blinder.api.Music.mapper.MusicCustomMapper;
import com.blinder.api.Music.service.MusicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/musics")
@RestController
@RequiredArgsConstructor
public class MusicController {
    private final MusicService musicService;
    private final MusicCustomMapper musicCustomMapper;

    @GetMapping("/search")
    public ResponseEntity<List<MusicResponseDto>> searchMusic(@RequestParam(name = "musicName") String musicName, @RequestParam(name = "limit", defaultValue = "50") int limit) throws JsonProcessingException {
        return new ResponseEntity<>(musicCustomMapper.musicDataToMusicResponseDto(musicService.searchMusic(musicName, limit)), HttpStatus.OK);
    }
}
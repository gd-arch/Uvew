package com.flux.uvew.controller;

import com.flux.uvew.dto.UploadThumbnailResponseDto;
import com.flux.uvew.dto.UploadVideoResponse;
import com.flux.uvew.dto.VideoDto;
import com.flux.uvew.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class VideoController {
    private final VideoService videoService;
    @GetMapping
    public List<VideoDto> getAllVideos(){
        return videoService.getAllVideos();
    }
    @PostMapping
    public ResponseEntity<UploadVideoResponse> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        if(Objects.nonNull(file) && !file.isEmpty()) {
            UploadVideoResponse responseDto = videoService.uploadVideo(file);
            return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
        }
        return ResponseEntity.unprocessableEntity().build();
    }
    @PostMapping("/thumbnail")
    public String uploadThumbnail(@RequestParam("file") MultipartFile file,@RequestParam("id") String id) throws IOException {
        if(Objects.nonNull(file) && !file.isEmpty()) {
            return videoService.uploadThumbnail(file,id);
        }
        return "";
    }


    //update video details
    @PutMapping
    public ResponseEntity<VideoDto> updateVideoDetails(@RequestBody VideoDto video) throws IOException {
        if (video != null) {
            VideoDto updatedVideo = videoService.updateVideo(video);
            return new ResponseEntity<VideoDto>(updatedVideo,HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }


}

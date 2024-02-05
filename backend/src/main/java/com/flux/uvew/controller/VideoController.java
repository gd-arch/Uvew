package com.flux.uvew.controller;

import com.flux.uvew.dto.UploadThumbnailDto;
import com.flux.uvew.dto.UploadVideoDto;
import com.flux.uvew.dto.VideoDto;
import com.flux.uvew.dto.VideoPageDto;
import com.flux.uvew.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class VideoController {
    private final VideoService videoService;
    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDto> getVideo(@PathVariable String videoId){
        return ResponseEntity.ok(videoService.getVideo(videoId));
    }
    @GetMapping
    public ResponseEntity<VideoPageDto> getVideos(@RequestParam(defaultValue = "0",required = true)int pageNo,
                                                  @RequestParam(defaultValue = "10",required = true)int pageSize,
                                                  @RequestParam(defaultValue = "",required = false)String searchQuery){

        return ResponseEntity.ok(videoService.getVideosByPagination(pageNo,pageSize,searchQuery));

    }
    @PostMapping
    public ResponseEntity<UploadVideoDto> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        if(Objects.nonNull(file) && !file.isEmpty()) {
            UploadVideoDto responseDto = videoService.uploadVideo(file);
            return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
        }
        return ResponseEntity.unprocessableEntity().build();
    }
    @PostMapping("/thumbnail")
    public ResponseEntity<UploadThumbnailDto> uploadThumbnail(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) throws IOException {
        if(Objects.nonNull(file) && !file.isEmpty()) {
            UploadThumbnailDto responseDto = videoService.uploadThumbnail(file,id);
            return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
        }
        return ResponseEntity.unprocessableEntity().build();
    }


    //update video details
    @PutMapping
    public ResponseEntity<VideoDto> updateVideoDetails(@Valid @RequestBody VideoDto video) {
        if (video != null) {
            VideoDto updatedVideo = videoService.updateVideo(video);
            return new ResponseEntity<>(updatedVideo,HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/like/{videoId}")
    public ResponseEntity<Map<String, String>> likeVideo(@PathVariable String videoId) {
        videoService.toggleLikeVideo(videoId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/dislike/{videoId}")
    public ResponseEntity<Map<String, String>> dislikeVideo(@PathVariable String videoId) {
        videoService.toggleDislikeVideo(videoId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/view/{videoId}")
    public ResponseEntity<Map<String, String>> incrementView(@PathVariable String videoId) {
        videoService.incrementView(videoId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}

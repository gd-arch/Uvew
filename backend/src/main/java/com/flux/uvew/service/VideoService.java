package com.flux.uvew.service;

import com.flux.uvew.Repository.VideoRepository;
import com.flux.uvew.dto.UploadThumbnailResponseDto;
import com.flux.uvew.dto.UploadVideoResponse;
import com.flux.uvew.dto.VideoDto;
import com.flux.uvew.exceptions.ResourceNotFoundException;
import com.flux.uvew.model.Video;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final ModelMapper modelMapper;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) throws IOException {
        String videoUrl = s3Service.uploadFile(multipartFile);
        Video video = new Video();
        video.setVideoUrl(videoUrl);
        video.setTitle(multipartFile.getName());
        Video savedVideo = videoRepository.save(video);
        return modelMapper.map(savedVideo, UploadVideoResponse.class);
    }
    public VideoDto updateVideo(VideoDto videoDto) {
        Video video = findVideoById(videoDto.getId());
        if(Strings.isNotEmpty(videoDto.getTitle()))
            video.setTitle(videoDto.getTitle());
        if(Objects.nonNull(videoDto.getTags()) && videoDto.getTags().size()>0)
            video.setTags(videoDto.getTags());
        if(Objects.nonNull(videoDto.getVideoStatus()))
            video.setVideoStatus(videoDto.getVideoStatus());
        Video updatedVideo= videoRepository.save(video);
        return modelMapper.map(updatedVideo, VideoDto.class);
    }

    public String uploadThumbnail(MultipartFile file,String id) throws IOException {
        Video video = findVideoById(id);
        String thumbnailUrl = s3Service.uploadFile(file);
        video.setThumbnailUrl(thumbnailUrl);
        Video savedVideo = videoRepository.save(video);
        return savedVideo.getThumbnailUrl();
    }

    public Video findVideoById(String id) {
        return videoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Video for id " + id + " not found"));
    }

    public List<VideoDto> getAllVideos() {
        return videoRepository.findAll()
                .stream()
                .map(video -> modelMapper.map(video, VideoDto.class))
                .collect(Collectors.toList());
    }

}

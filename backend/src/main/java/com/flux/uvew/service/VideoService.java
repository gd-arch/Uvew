package com.flux.uvew.service;

import com.flux.uvew.dto.UploadThumbnailDto;
import com.flux.uvew.dto.UploadVideoDto;
import com.flux.uvew.dto.VideoDto;
import com.flux.uvew.dto.VideoPageDto;
import com.flux.uvew.exceptions.ResourceNotFoundException;
import com.flux.uvew.exceptions.UserNotLoggedInException;
import com.flux.uvew.model.User;
import com.flux.uvew.model.Video;
import com.flux.uvew.repository.VideoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {
    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final MongoTemplate mongoTemplate;
    private static final int MAX_RETRY_COUNT = 3;

    public UploadVideoDto uploadVideo(MultipartFile multipartFile) throws IOException {
        String videoUrl = s3Service.uploadFile(multipartFile);
        Video video = new Video();
        video.setVideoUrl(videoUrl);
        video.setTitle(multipartFile.getName());
        User currentUser =userService.getCurrentUser();
        if(currentUser != null) {
            video.setUserSubId(currentUser.getSub());
            video.setAuthorName(currentUser.getFullName());
        }
        Video savedVideo;
        if(userService.getCurrentUser() != null){
            savedVideo = videoRepository.save(video);
        }else{
            throw new UserNotLoggedInException("User not logged in");
        }
        return modelMapper.map(savedVideo, UploadVideoDto.class);
    }
    public VideoDto updateVideo(VideoDto videoDto) {
        Video video = findVideoById(videoDto.getId());
        if(Strings.isNotEmpty(videoDto.getTitle()))
            video.setTitle(videoDto.getTitle());
        if(Objects.nonNull(videoDto.getTags()) && !videoDto.getTags().isEmpty())
            video.setTags(videoDto.getTags());
        if(Objects.nonNull(videoDto.getVideoStatus()))
            video.setVideoStatus(videoDto.getVideoStatus());
        if(Objects.nonNull(videoDto.getDescription()))
            video.setDescription(videoDto.getDescription());
        video.setActive(true);
        Video updatedVideo= videoRepository.save(video);
        return modelMapper.map(updatedVideo, VideoDto.class);
    }

    public UploadThumbnailDto uploadThumbnail( MultipartFile file, String id) throws IOException {
        Video video = findVideoById(id);
        String thumbnailUrl = s3Service.uploadFile(file);
        video.setThumbnailUrl(thumbnailUrl);
        Video savedVideo = videoRepository.save(video);
        return modelMapper.map(savedVideo, UploadThumbnailDto.class);
    }

    public Video findVideoById( String id) {
        return videoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Video for id " + id + " not found"));
    }

    public List<VideoDto> getAllVideos() {
        return videoRepository.findAll()
                .stream()
                .filter(Video::isActive)
                .map(video -> modelMapper.map(video, VideoDto.class))
                .toList();
    }

    public VideoPageDto getVideosByPagination(int pageNo, int pageSize,String searchQuery) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        if(searchQuery.equals("")) {
            Page<Video> page = videoRepository.findAll(pageRequest);

            return VideoPageDto.builder()
                    .content(page.getContent()
                            .stream()
                            .map(video -> modelMapper.map(video, VideoDto.class))
                            .toList())
                    .pageNumber(page.getNumber())
                    .totalPages(page.getTotalPages())
                    .isLastPage(page.isLast())
                    .pageSize(page.getSize())
                    .totalElements(page.getTotalElements())
                    .build();
        }else{
            TextCriteria criteria = TextCriteria.forDefaultLanguage()
                    .matchingAny(searchQuery);

            Query query = TextQuery.queryText(criteria)
                    .sortByScore()
                    .with(pageRequest);

            List<Video> videos = mongoTemplate.find(query, Video.class);
            long totalCount = mongoTemplate.count(query, Video.class);

            Page<Video> page = new PageImpl<Video>(videos, pageRequest, totalCount);

            return VideoPageDto.builder()
                    .content(page.getContent()
                            .stream()
                            .map(video -> modelMapper.map(video, VideoDto.class))
                            .toList())
                    .pageNumber(page.getNumber())
                    .totalPages(page.getTotalPages())
                    .isLastPage(page.isLast())
                    .pageSize(page.getSize())
                    .totalElements(page.getTotalElements())
                    .build();

        }


    }

    public VideoDto getVideo(@NonNull String id) {
        return modelMapper.map(findVideoById(id),VideoDto.class);
    }

    public void toggleLikeVideo( String videoId){
        if(isVideoValid(videoId)) {
            User currentUser = userService.getCurrentUser();
            if (currentUser != null) {
                Set<String> likedVideos = currentUser.getLikedVideos();
                if (likedVideos == null) {
                    currentUser.setLikedVideos(new HashSet<>());
                    likedVideos = currentUser.getLikedVideos();
                }
                if (likedVideos.contains(videoId)) {
                    //video already liked
                    likedVideos.remove(videoId);
                    decrementLikes(videoId);
                } else {
                    //video not liked
                    Set<String> dislikedVideos = currentUser.getDislikedVideos();
                    if (dislikedVideos == null) {
                        currentUser.setDislikedVideos(new HashSet<>());
                        dislikedVideos = currentUser.getDislikedVideos();
                    }
                    if (dislikedVideos.contains(videoId)) {
                        //video already disliked
                        dislikedVideos.remove(videoId);
                        decrementDislikes(videoId);
                    } else {
                        //video not disliked and not liked
                        likedVideos.add(videoId);
                        incrementLikes(videoId);
                    }
                }
                userService.updateUser(currentUser);
            }
        }
    }
    public void toggleDislikeVideo( String videoId){
        if(isVideoValid(videoId)) {
            User currentUser = userService.getCurrentUser();
            if (currentUser != null) {
                Set<String> dislikedVideos = currentUser.getDislikedVideos();
                if (dislikedVideos == null) {
                    currentUser.setDislikedVideos(new HashSet<>());
                    dislikedVideos = currentUser.getDislikedVideos();
                }
                if (dislikedVideos.contains(videoId)) {
                    //video already disliked
                    dislikedVideos.remove(videoId);
                    decrementDislikes(videoId);
                } else {
                    //video not disliked
                    Set<String> likedVideos = currentUser.getLikedVideos();
                    if (likedVideos == null) {
                        currentUser.setLikedVideos(new HashSet<>());
                        likedVideos = currentUser.getLikedVideos();
                    }
                    if (likedVideos.contains(videoId)) {
                        //video already liked
                        likedVideos.remove(videoId);
                        decrementLikes(videoId);
                    } else {
                        //video not liked and not disliked
                        dislikedVideos.add(videoId);
                        incrementDislikes(videoId);
                    }
                }
                userService.updateUser(currentUser);
            }
        }
    }

    public void incrementLikes(String videoId) {
        updateLikesWithRetry(videoId, 1);
    }

    public void decrementLikes(String videoId) {
        updateLikesWithRetry(videoId, -1);
    }
    public void incrementDislikes(String videoId) {
        updateDislikesWithRetry(videoId, 1);
    }

    public void decrementDislikes(String videoId) {
        updateDislikesWithRetry(videoId, -1);
    }

    private void updateLikesWithRetry(String videoId, int delta) {
        int retryCount = 0;
        while (retryCount < MAX_RETRY_COUNT) {
            try {
                Query query = new Query(Criteria.where("id").is(videoId));
                Update update = new Update().inc("likes", delta);
                mongoTemplate.findAndModify(query, update, Video.class);
                return; // Success, exit the method
            } catch (OptimisticLockingFailureException e) {
                retryCount++;
                if (retryCount >= MAX_RETRY_COUNT) {
                    log.error("Error while trying to update likes count in video");
                    throw e; // Rethrow the exception after max retries
                }
            }
        }
    }
    private void updateDislikesWithRetry(String videoId, int delta) {
        int retryCount = 0;
        while (retryCount < MAX_RETRY_COUNT) {
            try {
                Query query = new Query(Criteria.where("id").is(videoId));
                Update update = new Update().inc("dislikes", delta);
                mongoTemplate.findAndModify(query, update, Video.class);
                return; // Success, exit the method
            } catch (OptimisticLockingFailureException e) {
                retryCount++;
                if (retryCount >= MAX_RETRY_COUNT) {
                    log.error("Error while trying to update dislikes count in video");
                    throw e; // Rethrow the exception after max retries
                }
            }
        }
    }
    private void updateViewWithRetry(String videoId, int delta) {
        int retryCount = 0;
        while (retryCount < MAX_RETRY_COUNT) {
            try {
                Query query = new Query(Criteria.where("id").is(videoId));
                Update update = new Update().inc("views", delta);
                mongoTemplate.findAndModify(query, update, Video.class);
                return; // Success, exit the method
            } catch (OptimisticLockingFailureException e) {
                retryCount++;
                if (retryCount >= MAX_RETRY_COUNT) {
                    log.error("Error while trying to update view count in video");
                    throw e; // Rethrow the exception after max retries
                }
            }
        }
    }


    public void incrementView(String videoId) {
        if (isVideoValid(videoId)) {
            User currentUser = userService.getCurrentUser();
            if (currentUser != null) {
                LinkedList<String> videoHistory = currentUser.getVideoHistory();
                if (videoHistory == null) {
                    currentUser.setVideoHistory(new LinkedList<>());
                    videoHistory = currentUser.getVideoHistory();
                }
                if(videoHistory.isEmpty() || !videoHistory.getLast().equals(videoId)) {
                    videoHistory.add(videoId);
                    updateViewWithRetry(videoId, 1);
                    userService.updateUser(currentUser);
                }

            }
        }
    }


    private boolean isVideoValid(String videoId) {
        return videoId != null && videoRepository.findById(videoId).isPresent();
    }


}


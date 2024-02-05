package com.flux.uvew.repository;

import com.flux.uvew.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository  extends MongoRepository<Comment, String> {
    Optional<List<Comment>> findByVideoId(String videoId);

    @Query("{ 'videoId': ?0, 'active': true }")
    Page<Comment> findByVideoIdWithPagination(String videoId, Pageable pageable);
}

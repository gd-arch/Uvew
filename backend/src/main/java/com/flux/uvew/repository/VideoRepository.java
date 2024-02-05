package com.flux.uvew.repository;

import com.flux.uvew.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
    //ToDO: findAll active videos only
}


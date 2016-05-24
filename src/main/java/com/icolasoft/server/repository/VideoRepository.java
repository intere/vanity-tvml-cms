package com.icolasoft.server.repository;

import com.icolasoft.server.domain.Video;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Spring Data MongoDB repository for the Video entity.
 */
@SuppressWarnings("unused")
public interface VideoRepository extends MongoRepository<Video,String> {
    List<Video> findByTagName(String tagName);
}

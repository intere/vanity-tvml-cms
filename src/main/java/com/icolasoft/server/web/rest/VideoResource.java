package com.icolasoft.server.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.icolasoft.server.domain.Video;
import com.icolasoft.server.repository.VideoRepository;
import com.icolasoft.server.web.rest.util.HeaderUtil;
import com.icolasoft.server.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Video.
 */
@RestController
@RequestMapping("/api")
public class VideoResource {

    private final Logger log = LoggerFactory.getLogger(VideoResource.class);
        
    @Inject
    private VideoRepository videoRepository;
    
    /**
     * POST  /videos : Create a new video.
     *
     * @param video the video to create
     * @return the ResponseEntity with status 201 (Created) and with body the new video, or with status 400 (Bad Request) if the video has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/videos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Video> createVideo(@Valid @RequestBody Video video) throws URISyntaxException {
        log.debug("REST request to save Video : {}", video);
        if (video.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("video", "idexists", "A new video cannot already have an ID")).body(null);
        }
        Video result = videoRepository.save(video);
        return ResponseEntity.created(new URI("/api/videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("video", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /videos : Updates an existing video.
     *
     * @param video the video to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated video,
     * or with status 400 (Bad Request) if the video is not valid,
     * or with status 500 (Internal Server Error) if the video couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/videos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Video> updateVideo(@Valid @RequestBody Video video) throws URISyntaxException {
        log.debug("REST request to update Video : {}", video);
        if (video.getId() == null) {
            return createVideo(video);
        }
        Video result = videoRepository.save(video);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("video", video.getId().toString()))
            .body(result);
    }

    /**
     * GET  /videos : get all the videos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of videos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/videos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Video>> getAllVideos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Videos");
        Page<Video> page = videoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/videos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /videos/:id : get the "id" video.
     *
     * @param id the id of the video to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the video, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/videos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Video> getVideo(@PathVariable String id) {
        log.debug("REST request to get Video : {}", id);
        Video video = videoRepository.findOne(id);
        return Optional.ofNullable(video)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /videos/:id : delete the "id" video.
     *
     * @param id the id of the video to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/videos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVideo(@PathVariable String id) {
        log.debug("REST request to delete Video : {}", id);
        videoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("video", id.toString())).build();
    }

}

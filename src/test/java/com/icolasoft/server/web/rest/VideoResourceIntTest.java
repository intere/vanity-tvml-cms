package com.icolasoft.server.web.rest;

import com.icolasoft.server.VanityTvmlApp;
import com.icolasoft.server.domain.Video;
import com.icolasoft.server.repository.VideoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the VideoResource REST controller.
 *
 * @see VideoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VanityTvmlApp.class)
@WebAppConfiguration
@IntegrationTest
public class VideoResourceIntTest {

    private static final String DEFAULT_VIDEO_URL_STRING = "A";
    private static final String UPDATED_VIDEO_URL_STRING = "B";
    private static final String DEFAULT_BUTTON_TITLE = "A";
    private static final String UPDATED_BUTTON_TITLE = "B";
    private static final String DEFAULT_OVERVIEW_TITLE = "A";
    private static final String UPDATED_OVERVIEW_TITLE = "B";
    private static final String DEFAULT_OVERVIEW_SUBTITLE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_OVERVIEW_SUBTITLE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_OVERVIEW_DESCRIPTION = "A";
    private static final String UPDATED_OVERVIEW_DESCRIPTION = "B";
    private static final String DEFAULT_OVERVIEW_IMAGE_URL_STRING = "A";
    private static final String UPDATED_OVERVIEW_IMAGE_URL_STRING = "B";
    private static final String DEFAULT_TAG_NAME = "A";
    private static final String UPDATED_TAG_NAME = "B";

    @Inject
    private VideoRepository videoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVideoMockMvc;

    private Video video;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VideoResource videoResource = new VideoResource();
        ReflectionTestUtils.setField(videoResource, "videoRepository", videoRepository);
        this.restVideoMockMvc = MockMvcBuilders.standaloneSetup(videoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        videoRepository.deleteAll();
        video = new Video();
        video.setVideoURLString(DEFAULT_VIDEO_URL_STRING);
        video.setButtonTitle(DEFAULT_BUTTON_TITLE);
        video.setOverviewTitle(DEFAULT_OVERVIEW_TITLE);
        video.setOverviewSubtitle(DEFAULT_OVERVIEW_SUBTITLE);
        video.setOverviewDescription(DEFAULT_OVERVIEW_DESCRIPTION);
        video.setOverviewImageURLString(DEFAULT_OVERVIEW_IMAGE_URL_STRING);
        video.setTagName(DEFAULT_TAG_NAME);
    }

    @Test
    public void createVideo() throws Exception {
        int databaseSizeBeforeCreate = videoRepository.findAll().size();

        // Create the Video

        restVideoMockMvc.perform(post("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(video)))
                .andExpect(status().isCreated());

        // Validate the Video in the database
        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeCreate + 1);
        Video testVideo = videos.get(videos.size() - 1);
        assertThat(testVideo.getVideoURLString()).isEqualTo(DEFAULT_VIDEO_URL_STRING);
        assertThat(testVideo.getButtonTitle()).isEqualTo(DEFAULT_BUTTON_TITLE);
        assertThat(testVideo.getOverviewTitle()).isEqualTo(DEFAULT_OVERVIEW_TITLE);
        assertThat(testVideo.getOverviewSubtitle()).isEqualTo(DEFAULT_OVERVIEW_SUBTITLE);
        assertThat(testVideo.getOverviewDescription()).isEqualTo(DEFAULT_OVERVIEW_DESCRIPTION);
        assertThat(testVideo.getOverviewImageURLString()).isEqualTo(DEFAULT_OVERVIEW_IMAGE_URL_STRING);
        assertThat(testVideo.getTagName()).isEqualTo(DEFAULT_TAG_NAME);
    }

    @Test
    public void checkVideoURLStringIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setVideoURLString(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(video)))
                .andExpect(status().isBadRequest());

        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkButtonTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setButtonTitle(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(video)))
                .andExpect(status().isBadRequest());

        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOverviewTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setOverviewTitle(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(video)))
                .andExpect(status().isBadRequest());

        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOverviewDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setOverviewDescription(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(video)))
                .andExpect(status().isBadRequest());

        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOverviewImageURLStringIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setOverviewImageURLString(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(video)))
                .andExpect(status().isBadRequest());

        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTagNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setTagName(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(video)))
                .andExpect(status().isBadRequest());

        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVideos() throws Exception {
        // Initialize the database
        videoRepository.save(video);

        // Get all the videos
        restVideoMockMvc.perform(get("/api/videos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(video.getId())))
                .andExpect(jsonPath("$.[*].videoURLString").value(hasItem(DEFAULT_VIDEO_URL_STRING.toString())))
                .andExpect(jsonPath("$.[*].buttonTitle").value(hasItem(DEFAULT_BUTTON_TITLE.toString())))
                .andExpect(jsonPath("$.[*].overviewTitle").value(hasItem(DEFAULT_OVERVIEW_TITLE.toString())))
                .andExpect(jsonPath("$.[*].overviewSubtitle").value(hasItem(DEFAULT_OVERVIEW_SUBTITLE.toString())))
                .andExpect(jsonPath("$.[*].overviewDescription").value(hasItem(DEFAULT_OVERVIEW_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].overviewImageURLString").value(hasItem(DEFAULT_OVERVIEW_IMAGE_URL_STRING.toString())))
                .andExpect(jsonPath("$.[*].tagName").value(hasItem(DEFAULT_TAG_NAME.toString())));
    }

    @Test
    public void getVideo() throws Exception {
        // Initialize the database
        videoRepository.save(video);

        // Get the video
        restVideoMockMvc.perform(get("/api/videos/{id}", video.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(video.getId()))
            .andExpect(jsonPath("$.videoURLString").value(DEFAULT_VIDEO_URL_STRING.toString()))
            .andExpect(jsonPath("$.buttonTitle").value(DEFAULT_BUTTON_TITLE.toString()))
            .andExpect(jsonPath("$.overviewTitle").value(DEFAULT_OVERVIEW_TITLE.toString()))
            .andExpect(jsonPath("$.overviewSubtitle").value(DEFAULT_OVERVIEW_SUBTITLE.toString()))
            .andExpect(jsonPath("$.overviewDescription").value(DEFAULT_OVERVIEW_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.overviewImageURLString").value(DEFAULT_OVERVIEW_IMAGE_URL_STRING.toString()))
            .andExpect(jsonPath("$.tagName").value(DEFAULT_TAG_NAME.toString()));
    }

    @Test
    public void getNonExistingVideo() throws Exception {
        // Get the video
        restVideoMockMvc.perform(get("/api/videos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateVideo() throws Exception {
        // Initialize the database
        videoRepository.save(video);
        int databaseSizeBeforeUpdate = videoRepository.findAll().size();

        // Update the video
        Video updatedVideo = new Video();
        updatedVideo.setId(video.getId());
        updatedVideo.setVideoURLString(UPDATED_VIDEO_URL_STRING);
        updatedVideo.setButtonTitle(UPDATED_BUTTON_TITLE);
        updatedVideo.setOverviewTitle(UPDATED_OVERVIEW_TITLE);
        updatedVideo.setOverviewSubtitle(UPDATED_OVERVIEW_SUBTITLE);
        updatedVideo.setOverviewDescription(UPDATED_OVERVIEW_DESCRIPTION);
        updatedVideo.setOverviewImageURLString(UPDATED_OVERVIEW_IMAGE_URL_STRING);
        updatedVideo.setTagName(UPDATED_TAG_NAME);

        restVideoMockMvc.perform(put("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedVideo)))
                .andExpect(status().isOk());

        // Validate the Video in the database
        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeUpdate);
        Video testVideo = videos.get(videos.size() - 1);
        assertThat(testVideo.getVideoURLString()).isEqualTo(UPDATED_VIDEO_URL_STRING);
        assertThat(testVideo.getButtonTitle()).isEqualTo(UPDATED_BUTTON_TITLE);
        assertThat(testVideo.getOverviewTitle()).isEqualTo(UPDATED_OVERVIEW_TITLE);
        assertThat(testVideo.getOverviewSubtitle()).isEqualTo(UPDATED_OVERVIEW_SUBTITLE);
        assertThat(testVideo.getOverviewDescription()).isEqualTo(UPDATED_OVERVIEW_DESCRIPTION);
        assertThat(testVideo.getOverviewImageURLString()).isEqualTo(UPDATED_OVERVIEW_IMAGE_URL_STRING);
        assertThat(testVideo.getTagName()).isEqualTo(UPDATED_TAG_NAME);
    }

    @Test
    public void deleteVideo() throws Exception {
        // Initialize the database
        videoRepository.save(video);
        int databaseSizeBeforeDelete = videoRepository.findAll().size();

        // Get the video
        restVideoMockMvc.perform(delete("/api/videos/{id}", video.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeDelete - 1);
    }
}

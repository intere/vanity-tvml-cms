package com.icolasoft.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Video.
 */

@Document(collection = "video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 1)
    @Field("video_url_string")
    private String videoURLString;

    @NotNull
    @Size(min = 1)
    @Field("button_title")
    private String buttonTitle;

    @NotNull
    @Size(min = 1, max = 50)
    @Field("overview_title")
    private String overviewTitle;

    @Size(max = 80)
    @Field("overview_subtitle")
    private String overviewSubtitle;

    @NotNull
    @Size(min = 1)
    @Field("overview_description")
    private String overviewDescription;

    @NotNull
    @Size(min = 1)
    @Field("overview_image_url_string")
    private String overviewImageURLString;

    @NotNull
    @Size(min = 1)
    @Field("tag_name")
    private String tagName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoURLString() {
        return videoURLString;
    }

    public void setVideoURLString(String videoURLString) {
        this.videoURLString = videoURLString;
    }

    public String getButtonTitle() {
        return buttonTitle;
    }

    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

    public String getOverviewTitle() {
        return overviewTitle;
    }

    public void setOverviewTitle(String overviewTitle) {
        this.overviewTitle = overviewTitle;
    }

    public String getOverviewSubtitle() {
        return overviewSubtitle;
    }

    public void setOverviewSubtitle(String overviewSubtitle) {
        this.overviewSubtitle = overviewSubtitle;
    }

    public String getOverviewDescription() {
        return overviewDescription;
    }

    public void setOverviewDescription(String overviewDescription) {
        this.overviewDescription = overviewDescription;
    }

    public String getOverviewImageURLString() {
        return overviewImageURLString;
    }

    public void setOverviewImageURLString(String overviewImageURLString) {
        this.overviewImageURLString = overviewImageURLString;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Video video = (Video) o;
        if(video.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, video.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Video{" +
            "id=" + id +
            ", videoURLString='" + videoURLString + "'" +
            ", buttonTitle='" + buttonTitle + "'" +
            ", overviewTitle='" + overviewTitle + "'" +
            ", overviewSubtitle='" + overviewSubtitle + "'" +
            ", overviewDescription='" + overviewDescription + "'" +
            ", overviewImageURLString='" + overviewImageURLString + "'" +
            ", tagName='" + tagName + "'" +
            '}';
    }
}

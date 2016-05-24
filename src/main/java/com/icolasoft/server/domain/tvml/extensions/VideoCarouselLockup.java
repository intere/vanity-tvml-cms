package com.icolasoft.server.domain.tvml.extensions;

import com.icolasoft.server.domain.tvml.generated.Document.ShowcaseTemplate.Carousel.Section.Lockup;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by einternicola on 5/23/16.
 */
public class VideoCarouselLockup extends Lockup {
    private String videoURLString;

    @XmlAttribute
    public String getVideoURLString() {
        return videoURLString;
    }

    public void setVideoURLString(String videoURLString) {
        this.videoURLString = videoURLString;
    }
}

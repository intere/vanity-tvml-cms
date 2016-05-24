package com.icolasoft.server.web.rest.helpers;

import com.icolasoft.server.domain.Tag;
import com.icolasoft.server.domain.Video;
import com.icolasoft.server.repository.VideoRepository;
import com.icolasoft.server.domain.tvml.generated.Document;
import com.icolasoft.server.domain.tvml.generated.Document.ShowcaseTemplate;
import com.icolasoft.server.domain.tvml.generated.Document.ShowcaseTemplate.Banner;
import com.icolasoft.server.domain.tvml.generated.Document.ShowcaseTemplate.Banner.Row;
import com.icolasoft.server.domain.tvml.generated.Document.ShowcaseTemplate.Carousel;
import com.icolasoft.server.domain.tvml.generated.Document.ShowcaseTemplate.Carousel.Section;
import com.icolasoft.server.domain.tvml.generated.Document.ShowcaseTemplate.Carousel.Section.Lockup.Img;

import com.icolasoft.server.domain.tvml.extensions.VideoCarouselLockup;

import java.util.List;


/**
 * Created by einternicola on 5/23/16.
 */
public class TagTVML {

    public static Document getTagTVML(Tag tag, VideoRepository videoRepository) {
        Document doc = new Document();
        ShowcaseTemplate showcase = new ShowcaseTemplate();
        showcase.setBanner(new Banner());
        showcase.getBanner().setTitle(tag.getName() + " Videos");
        showcase.setCarousel(new Carousel());
        showcase.getCarousel().setSection(new Section());

        List<Video> videos = videoRepository.findByTagName(tag.getName());
        if(videos.size() > 0 ) {
            for(Video video : videos) {
                VideoCarouselLockup lockup = new VideoCarouselLockup();
                lockup.setImg(new Img());
                lockup.getImg().setSrc(video.getOverviewImageURLString());
                lockup.getImg().setWidth((short)824);
                lockup.getImg().setHeight((short)466);
                lockup.setTitle(video.getOverviewTitle());
                lockup.setSubtitle(video.getOverviewSubtitle());
                lockup.setDescription(video.getOverviewDescription());

                showcase.getCarousel().getSection().getLockup().add(lockup);
            }
        } else {
            // TODO: Use the Alert to show an error
        }

        doc.setShowcaseTemplate(showcase);
        return doc;
    }
}

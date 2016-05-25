package com.icolasoft.server.web.rest.helpers;

import com.icolasoft.server.domain.Tag;
import com.icolasoft.server.domain.Video;
import com.icolasoft.server.repository.VideoRepository;
import com.icolasoft.server.domain.tvml.generated.Document;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.Banner;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.Banner.Background;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Section;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Section.Header;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Section.ListItemLockup;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Section.ListItemLockup.RelatedContent;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Section.ListItemLockup.RelatedContent.Lockup;

import com.icolasoft.server.domain.tvml.extensions.VideoCarouselLockup;

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Created by einternicola on 5/23/16.
 */
public class TagTVML {

    public static Document getTagTVML(Tag tag, VideoRepository videoRepository) {
        Document doc = new Document();
        ListTemplate listTemplate = new ListTemplate();
        listTemplate.setBanner(new Banner());
        listTemplate.getBanner().setBackground(new Background());
        listTemplate.getBanner().getBackground().setImg(new Background.Img());
        listTemplate.getBanner().getBackground().getImg().setSrc(tag.getTagImageURLString());
        listTemplate.getBanner().getBackground().getImg().setWidth(1920);
        listTemplate.getBanner().getBackground().getImg().setHeight(360);

        listTemplate.setList(new List());
        listTemplate.getList().setSection(new Section());
        listTemplate.getList().getSection().setHeader(new Header());
        listTemplate.getList().getSection().getHeader().setTitle(tag.getSubtitle());

        for(Video video : videoRepository.findByTagName(tag.getName())) {
            ListItemLockup lockup = new ListItemLockup();
            lockup.setVideoUrlString(video.getVideoURLString());
            lockup.setTitle(video.getButtonTitle());
            lockup.setRelatedContent(new RelatedContent());
            lockup.getRelatedContent().setLockup(new Lockup());
            lockup.getRelatedContent().getLockup().setImg(new Lockup.Img());
            lockup.getRelatedContent().getLockup().getImg().setSrc(video.getOverviewImageURLString());
            lockup.getRelatedContent().getLockup().getImg().setWidth(857);
            lockup.getRelatedContent().getLockup().getImg().setHeight(482);
            lockup.getRelatedContent().getLockup().setTitle(video.getOverviewTitle());
            lockup.getRelatedContent().getLockup().setSubtitle(video.getOverviewSubtitle());
            lockup.getRelatedContent().getLockup().setDescription(new Lockup.Description());
            lockup.getRelatedContent().getLockup().getDescription().setValue(video.getOverviewDescription());

            listTemplate.getList().getSection().getListItemLockup().add(lockup);
        }

//        ShowcaseTemplate showcase = new ShowcaseTemplate();
//        showcase.setBanner(new Banner());
//        showcase.getBanner().setTitle(tag.getName() + " Videos");
//        showcase.setCarousel(new Carousel());
//        showcase.getCarousel().setSection(new Section());
//
//        List<Video> videos = videoRepository.findByTagName(tag.getName());
//        if(videos.size() > 0 ) {
//            for(Video video : videos) {
//                Lockup lockup = new Lockup();
//                lockup.setVideoURLString(video.getVideoURLString());
//                lockup.setImg(new Img());
//                lockup.getImg().setSrc(video.getOverviewImageURLString());
//                lockup.getImg().setWidth((short)824);
//                lockup.getImg().setHeight((short)466);
//                lockup.setTitle(video.getOverviewTitle());
//                lockup.setSubtitle(video.getOverviewSubtitle());
//                lockup.setDescription(video.getOverviewDescription());
//
//                showcase.getCarousel().getSection().getLockup().add(lockup);
//            }
//        } else {
//            // TODO: Use the Alert to show an error
//        }
//
//        doc.setShowcaseTemplate(showcase);
        doc.setListTemplate(listTemplate);
        return doc;
    }
}




//@XmlAttribute(required=false)
//private String videoURLString;
//
//public String getVideoURLString() {
//    return videoURLString;
//}
//
//public void setVideoURLString(String videoURLString) {
//    this.videoURLString = videoURLString;
//}

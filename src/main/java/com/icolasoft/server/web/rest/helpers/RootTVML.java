package com.icolasoft.server.web.rest.helpers;

import com.icolasoft.server.domain.Tag;
import com.icolasoft.server.repository.TagRepository;
import com.icolasoft.server.domain.tvml.generated.Document;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Header;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Section;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Section.ListItemLockup;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Section.ListItemLockup.RelatedContent;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Section.ListItemLockup.RelatedContent.Lockup;
import com.icolasoft.server.domain.tvml.generated.Document.ListTemplate.List.Section.ListItemLockup.RelatedContent.Lockup.*;
import javassist.runtime.Desc;

/**
 * Created by einternicola on 5/23/16.
 */
public class RootTVML {

    public static Document buildRootDocument(TagRepository tagRepository, String baseURL) {
        Document doc = new Document();
        ListTemplate template = new ListTemplate();
        doc.setListTemplate(template);
        template.setList(new List());
        template.getList().setHeader(new Header());
        template.getList().getHeader().setTitle("Vanity Videos");
        template.getList().setSection(new Section());

        for(Tag tag : tagRepository.findAll()) {
            ListItemLockup lockup = new ListItemLockup();
            lockup.setDocumentURL(baseURL + "/api/tvml/" + tag.getId());
            lockup.setTitle(tag.getName());
            lockup.setRelatedContent(new RelatedContent());
            lockup.getRelatedContent().setLockup(new Lockup());
            Img img = new Img();
            img.setSrc(tag.getTagImageURLString());
            img.setWidth((short)824);
            img.setHeight((short)466);
            lockup.getRelatedContent().getLockup().setImg(img);

            Description desc = new Description();
            desc.setClazz("descriptionLayout");
            desc.getContent().add(tag.getDescription());
            lockup.getRelatedContent().getLockup().setDescription(desc);

            template.getList().getSection().getListItemLockup().add(lockup);
        }

        return doc;
    }

}

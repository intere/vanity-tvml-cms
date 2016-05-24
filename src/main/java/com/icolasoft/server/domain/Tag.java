package com.icolasoft.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tag.
 */

@Document(collection = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 1)
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @NotNull
    @Size(min = 1)
    @Field("tag_image_url_string")
    private String tagImageURLString;

    @Field("subtitle")
    private String subtitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagImageURLString() {
        return tagImageURLString;
    }

    public void setTagImageURLString(String tagImageURLString) {
        this.tagImageURLString = tagImageURLString;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        if(tag.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", tagImageURLString='" + tagImageURLString + "'" +
            ", subtitle='" + subtitle + "'" +
            '}';
    }
}

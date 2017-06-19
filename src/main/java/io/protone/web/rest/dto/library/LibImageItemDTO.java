package io.protone.web.rest.dto.library;

/**
 * Created by lukaszozimek on 19/06/2017.
 */
public class LibImageItemDTO {

    private Long id;

    private String name;

    private String publicUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }
}

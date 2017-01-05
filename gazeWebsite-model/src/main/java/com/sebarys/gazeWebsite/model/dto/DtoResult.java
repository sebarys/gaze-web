package com.sebarys.gazeWebsite.model.dto;

/**
 * Created by Sebastian on 2016-12-18.
 */
public class DtoResult {

    private Long id;

    private Long created;

    private String attachmentPath;

    private Long stimulId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public Long getStimulId() {
        return stimulId;
    }

    public void setStimulId(Long stimulId) {
        this.stimulId = stimulId;
    }
}

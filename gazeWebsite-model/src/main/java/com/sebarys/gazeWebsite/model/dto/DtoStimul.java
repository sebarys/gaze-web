package com.sebarys.gazeWebsite.model.dto;

import java.util.Set;

/**
 * Created by Sebastian on 2016-11-19.
 */
public class DtoStimul {

    private Long id;

    private String name;

    private Long created;

    private String attachmentsPath;

    private Set<Long> attachmentIds;

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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getAttachmentsPath() {
        return attachmentsPath;
    }

    public void setAttachmentsPath(String attachmentsPath) {
        this.attachmentsPath = attachmentsPath;
    }

    public Set<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(Set<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }
}

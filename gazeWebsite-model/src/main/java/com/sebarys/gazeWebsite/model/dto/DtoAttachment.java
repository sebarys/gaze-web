package com.sebarys.gazeWebsite.model.dto;

public class DtoAttachment {

    private Long id;

    private String name;

    private String attachmentType;

    private long stimulId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Long getStimulId() {
        return stimulId;
    }

    public void setStimulId(Long stimulId) {
        this.stimulId = stimulId;
    }
}

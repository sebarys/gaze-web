package com.sebarys.gazeWebsite.model.dto;

import java.util.Map;

public class DtoResult {

    private Long id;

    private Long created;

    private String name;

    private String attachmentPath;

    private Long stimulId;

    private Map<String, String> profile;

    public Map<String, String> getProfile()
    {
        return profile;
    }

    public void setProfile(final Map<String, String> profile)
    {
        this.profile = profile;
    }

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

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
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

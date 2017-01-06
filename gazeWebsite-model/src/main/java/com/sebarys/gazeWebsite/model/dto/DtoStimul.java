package com.sebarys.gazeWebsite.model.dto;

import java.util.Map;
import java.util.Set;

public class DtoStimul {

    private Long id;

    private String name;

    private Long created;

    private String attachmentsPath;

    private Set<DtoAttachment> attachments;

    private Set<DtoResult> results;

    private Map<String, String> profile;

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

    public Set<DtoAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<DtoAttachment> attachments) {
        this.attachments = attachments;
    }

    public Set<DtoResult> getResults()
    {
        return results;
    }

    public void setResults(final Set<DtoResult> results)
    {
        this.results = results;
    }

    public Map<String, String> getProfile()
    {
        return profile;
    }

    public void setProfile(final Map<String, String> profile)
    {
        this.profile = profile;
    }
}

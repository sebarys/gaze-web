package com.sebarys.gazeWebsite.model.dbo;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.*;

@Entity
public class Stimul {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private Date created;

    private String attachmentsPath;

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, String> profile;

    @OneToMany(mappedBy = "stimul", cascade = CascadeType.ALL)
    private Set<Attachment> attachments;

    @OneToMany(mappedBy = "stimul", cascade = CascadeType.REMOVE)
    private Set<Result> results;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAttachmentsPath() {
        return attachmentsPath;
    }

    public void setAttachmentsPath(String attachmentsPath) {
        this.attachmentsPath = attachmentsPath;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Set<Result> getResults()
    {
        return results;
    }

    public void setResults(final Set<Result> results)
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
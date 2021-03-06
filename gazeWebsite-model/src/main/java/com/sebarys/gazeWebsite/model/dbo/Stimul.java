package com.sebarys.gazeWebsite.model.dbo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Stimul {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private Date created;

    private String attachmentsPath;

    @OneToMany(mappedBy = "stimul", cascade = CascadeType.ALL)
    private Set<Attachment> attachments;

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
}
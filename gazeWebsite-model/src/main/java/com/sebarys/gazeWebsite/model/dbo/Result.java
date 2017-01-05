package com.sebarys.gazeWebsite.model.dbo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Sebastian on 2016-12-18.
 */
@Entity
public class Result {

    @Id
    @GeneratedValue
    private long id;

    private Date created;

    private String attachmentPath;

    private  Long stimulId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
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

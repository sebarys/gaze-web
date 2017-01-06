package com.sebarys.gazeWebsite.model.dbo;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.Date;
import java.util.Map;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Result {

    @Id
    @GeneratedValue
    private long id;

    private Date created;

    private String attachmentPath;

    @ManyToOne
    @JoinColumn(name = "stimul_id")
    private Stimul stimul;

    private String name;

    @ElementCollection
    @Cascade(value={CascadeType.ALL})
    private Map<String, String> profile;

    public Map<String, String> getProfile()
    {
        return profile;
    }

    public void setProfile(final Map<String, String> profile)
    {
        this.profile = profile;
    }

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

    public Stimul getStimul() {
        return stimul;
    }

    public void setStimul(Stimul stimul) {
        this.stimul = stimul;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }
}

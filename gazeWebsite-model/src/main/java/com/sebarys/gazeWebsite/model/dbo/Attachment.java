package com.sebarys.gazeWebsite.model.dbo;

import javax.persistence.*;


@Entity
public class Attachment {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "stimul_id")
    private Stimul stimul;

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

    public Stimul getStimul() {
        return stimul;
    }

    public void setStimul(Stimul stimul) {
        this.stimul = stimul;
    }

}

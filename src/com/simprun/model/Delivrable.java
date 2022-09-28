package com.simprun.model;

import java.util.Date;
import java.util.UUID;

public class Delivrable implements IObjectable {
    private final String id;
    private final String link;
    private final Date createdAt;
    private final Brief brief;
    private final Apprenant apprenant;

    public Delivrable(String link, Date createdAt, Brief brief, Apprenant apprenant) {
        this.id = UUID.randomUUID().toString();
        this.link = link;
        this.createdAt = createdAt;
        this.brief = brief;
        this.apprenant = apprenant;
    }

    public String getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Brief getBrief() {
        return brief;
    }

    public Apprenant getApprenant() {
        return apprenant;
    }

    @Override
    public String toString() {
        return "Delivrable {" +
                "id='" + id + '\'' +
                ", link='" + link + '\'' +
                ", createdAt='" + createdAt.toString() + '\'' +
                ", brief=" + brief.getName() +
                ", apprenant=" + apprenant.getName() +
                '}';
    }
}

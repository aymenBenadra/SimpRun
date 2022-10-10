package com.simprun.model;

import com.simprun.visitor.IDeserializeVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class Deliverable implements IObjectable, ISerializable, IDeserializable {
    private String id;
    private String link;
    private Date createdAt;
    private Brief brief;
    private Apprenant apprenant;

    public Deliverable(String link, Brief brief, Apprenant apprenant) {
        this.id = UUID.randomUUID().toString();
        this.link = link;
        this.createdAt = new Date();
        this.brief = brief;
        this.apprenant = apprenant;
    }

    public Deliverable() {}

    // Getters
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
    public String getId() {
        return id;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setBrief(Brief brief) {
        this.brief = brief;
    }

    public void setApprenant(Apprenant apprenant) {
        this.apprenant = apprenant;
    }

    // Helpers
    @Override
    public String toString() {
        return "DeliverableDAO {" +
                "id='" + id + '\'' +
                ", link='" + link + '\'' +
                ", createdAt='" + createdAt.toString() + '\'' +
                ", brief=" + brief.getTitle() +
                ", apprenant=" + apprenant.getName() +
                '}';
    }

    @Override
    public String serializeFields() {
        return "id,link,createdAt,briefID,apprenantID";
    }

    @Override
    public String serializeValues() {
        return String.format("'%s','%s','%s','%s','%s'", id, link, createdAt.toString(),
                brief != null ? brief.getId() : "NULL", apprenant != null ? apprenant.getId() : "NULL");
    }

    @Override
    public void accept(IDeserializeVisitor visitor, ResultSet resultSet) {
        try {
            visitor.visit(this, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

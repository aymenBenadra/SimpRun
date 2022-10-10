package com.simprun.model;

import com.simprun.visitor.IDeserializeVisitor;
import com.simprun.visitor.ISerializeVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Brief implements IObjectable, ISerializable, IDeserializable {

    private String id;
    private String title;
    private String description;
    private Date deadline;
    private BriefStatus status;
    private Promo promo;

    public Brief() {}

    public Brief(String title, String description, Promo promo, Date deadline) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = BriefStatus.Active;
        this.promo = promo;
    }

    // Getters
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Date getDeadline() {
        return deadline;
    }
    public BriefStatus getStatus() {
        return status;
    }
    public Promo getPromo() {
        return promo;
    }
    @Override
    public String getId() {
        return id;
    }

    // Setters
    public void setStatus(BriefStatus status) {
        this.status = status;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    // Helpers
    @Override
    public String toString() {
        return "Brief {" +
                "id='" + id + '\'' +
                ", name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", promo=" + promo.getName() + " " + promo.getYear() +
                ", deadline='" + deadline.toString() + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public void accept(IDeserializeVisitor visitor, ResultSet resultSet) {
        try {
            visitor.visit(this, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, String> accept(ISerializeVisitor visitor) {
        return visitor.visit(this);
    }
}

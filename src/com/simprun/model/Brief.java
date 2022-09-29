package com.simprun.model;

import com.simprun.dao.MemoryCollectionDriver;

import java.util.Date;
import java.util.UUID;

public class Brief implements IObjectable {

    private final String id;
    private final String name;
    private final String description;
    private final Date deadline;
    private final BriefStatus status;
    private final Promo promo;
    private final MemoryCollectionDriver<Delivrable> delivrables;

    public Brief(String name, String description, Promo promo, Date deadline, BriefStatus status, MemoryCollectionDriver<Delivrable> delivrables) {
        this.delivrables = delivrables;
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.promo = promo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public MemoryCollectionDriver<Delivrable> getDelivrables() {
        return delivrables;
    }

    @Override
    public String toString() {
        return "Brief {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", promo=" + promo.getName() + " " + promo.getYear() +
                ", deadline='" + deadline.toString() + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
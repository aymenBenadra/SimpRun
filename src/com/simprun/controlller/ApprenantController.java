package com.simprun.controlller;

import com.simprun.dao.IDAO;
import com.simprun.model.*;

public class ApprenantController {
    private final IDAO<Brief> briefs;
    private final IDAO<Deliverable> deliverables;

    public ApprenantController(IDAO<Brief> briefs, IDAO<Deliverable> deliverables) {
        this.briefs = briefs;
        this.deliverables = deliverables;
    }

    public String[] getBriefs(Promo promo) {
        return briefs.getAll().stream()
                .filter(brief -> brief.getPromo().equals(promo) && brief.getStatus() == BriefStatus.Active)
                .map(Brief::getTitle)
                .toArray(String[]::new);
    }

    public Brief getBrief(String name) {
        return briefs.getByTitle(name);
    }

    public boolean addDeliverable(String briefTitle, String link, Apprenant apprenant) {
        Brief brief = getBrief(briefTitle);
        if (brief != null && brief.getStatus() == BriefStatus.Active) {
            Deliverable deliverable = new Deliverable(link, brief, apprenant);
            deliverables.add(deliverable);
            return true;
        }
        return false;
    }
}

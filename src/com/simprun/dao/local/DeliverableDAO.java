package com.simprun.dao.local;

import com.simprun.model.Deliverable;

import java.util.ArrayList;

public class DeliverableDAO extends MemoryCollectionDAO<Deliverable> {
    public DeliverableDAO() {
        super();
    }

    public DeliverableDAO(ArrayList<Deliverable> entities) {
        super(entities);
    }
}

package com.simprun.visitor;

import com.simprun.model.*;

import java.util.HashMap;

public interface ISerializeVisitor {
    HashMap<String, String> visit(Admin admin);

    HashMap<String, String> visit(Formateur formateur);

    HashMap<String, String> visit(Apprenant apprenant);

    HashMap<String, String> visit(Brief brief);

    HashMap<String, String> visit(Deliverable deliverable);

    HashMap<String, String> visit(Promo promo);
}

package com.simprun.visitor;

import com.simprun.model.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class SerializeVisitor implements ISerializeVisitor{
    private static SerializeVisitor instance;
    private SerializeVisitor() {}

    public static SerializeVisitor getInstance() {
        if (instance == null) {
            instance = new SerializeVisitor();
        }
        return instance;
    }

    private HashMap<String, String> serializeUser(User user) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        map.put("password", user.getPassword());
        map.put("role", user.getClass().getSimpleName());
        return map;
    }

    @Override
    public HashMap<String, String> visit(Admin admin) {
        HashMap<String, String> map = serializeUser(admin);
        map.put("promoID", null);
        return map;
    }

    @Override
    public HashMap<String, String> visit(Formateur formateur) {
        HashMap<String, String> map = serializeUser(formateur);
        map.put("promoID", formateur.getPromo() != null ? formateur.getPromo().getId() : null);
        return map;
    }

    @Override
    public HashMap<String, String> visit(Apprenant apprenant) {
        HashMap<String, String> map = serializeUser(apprenant);
        map.put("promoID", apprenant.getPromo() != null ? apprenant.getPromo().getId() : null);
        return map;
    }

    @Override
    public HashMap<String, String> visit(Brief brief) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", brief.getId());
        map.put("title", brief.getTitle());
        map.put("description", brief.getDescription());
        map.put("deadline", brief.getDeadline().toString());
        map.put("status", brief.getStatus().toString());
        map.put("promoID", brief.getPromo() != null ? brief.getPromo().getId() : null);
        return map;
    }

    @Override
    public HashMap<String, String> visit(Deliverable deliverable) {
        HashMap<String, String> map = new HashMap<>();
        map.put("link", deliverable.getLink());
        map.put("createdAt", new SimpleDateFormat("yyyy-MM-dd")
                .format(deliverable.getCreatedAt().getTime()));
        map.put("briefID", deliverable.getBrief() != null ? deliverable.getBrief().getId() : null);
        map.put("apprenantID", deliverable.getApprenant() != null ? deliverable.getApprenant().getId() : null);
        return map;
    }

    @Override
    public HashMap<String, String> visit(Promo promo) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", promo.getId());
        map.put("name", promo.getName());
        map.put("year", promo.getYear() + "");
        map.put("formateurID", promo.getFormateur() != null ? promo.getFormateur().getId() : null);
        return map;
    }
}

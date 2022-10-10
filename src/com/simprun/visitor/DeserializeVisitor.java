package com.simprun.visitor;

import com.simprun.dao.IDAO;
import com.simprun.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeserializeVisitor implements IDeserializeVisitor{
    private static DeserializeVisitor instance;
    private static IDAO<Admin> adminIDAO;
    private static IDAO<Apprenant> apprenantIDAO;
    private static IDAO<Formateur> formateurIDAO;
    private static IDAO<Brief> briefIDAO;
    private static IDAO<Promo> promoIDAO;
    private static IDAO<Deliverable> deliverableIDAO;
    private DeserializeVisitor() {}

    public static DeserializeVisitor getInstance() {
        if (instance == null) {
            instance = new DeserializeVisitor();
        }
        return instance;
    }

    public static void setDrivers(IDAO<Admin> adminIDAO, IDAO<Apprenant> apprenantIDAO, IDAO<Formateur> formateurIDAO, IDAO<Brief> briefIDAO, IDAO<Promo> promoIDAO, IDAO<Deliverable> deliverableIDAO) {
        DeserializeVisitor.adminIDAO = adminIDAO;
        DeserializeVisitor.apprenantIDAO = apprenantIDAO;
        DeserializeVisitor.formateurIDAO = formateurIDAO;
        DeserializeVisitor.briefIDAO = briefIDAO;
        DeserializeVisitor.promoIDAO = promoIDAO;
        DeserializeVisitor.deliverableIDAO = deliverableIDAO;
    }

    private void setUserFields(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
    }

    @Override
    public void visit(Admin admin, ResultSet resultSet) throws SQLException {
        setUserFields(admin, resultSet);
        adminIDAO.getLocalDAO().add(admin);
    }

    @Override
    public void visit(Formateur formateur, ResultSet resultSet) throws SQLException {
        setUserFields(formateur, resultSet);
        formateurIDAO.getLocalDAO().add(formateur);
        if (formateur.getPromo() == null) {
            formateur.setPromo(promoIDAO.get(resultSet.getString("promoID")));
        }
    }

    @Override
    public void visit(Apprenant apprenant, ResultSet resultSet) throws SQLException {
        setUserFields(apprenant, resultSet);
        apprenantIDAO.getLocalDAO().add(apprenant);
        if (apprenant.getPromo() == null) {
            apprenant.setPromo(promoIDAO.get(resultSet.getString("promoID")));
        }
    }

    @Override
    public void visit(Brief brief, ResultSet resultSet) throws SQLException {
        brief.setId(resultSet.getString("id"));
        brief.setTitle(resultSet.getString("title"));
        brief.setDescription(resultSet.getString("description"));
        brief.setDeadline(resultSet.getDate("deadline"));
        brief.setStatus(BriefStatus.valueOf(resultSet.getString("status")));
        briefIDAO.getLocalDAO().add(brief);
        if (brief.getPromo() == null) {
            brief.setPromo(promoIDAO.get(resultSet.getString("promoID")));
        }
    }

    @Override
    public void visit(Deliverable deliverable, ResultSet resultSet) throws SQLException {
        deliverable.setLink(resultSet.getString("link"));
        deliverable.setCreatedAt(resultSet.getDate("createdAt"));
        deliverableIDAO.getLocalDAO().add(deliverable);
        if (deliverable.getApprenant() == null) {
            deliverable.setApprenant(apprenantIDAO.get(resultSet.getString("apprenantID")));
        }
        if (deliverable.getBrief() == null) {
            deliverable.setBrief(briefIDAO.get(resultSet.getString("briefID")));
        }
    }

    @Override
    public void visit(Promo promo, ResultSet resultSet) throws SQLException {
        promo.setId(resultSet.getString("id"));
        promo.setName(resultSet.getString("name"));
        promo.setYear(resultSet.getInt("year"));
        promoIDAO.getLocalDAO().add(promo);
        if (promo.getFormateur() == null) {
            promo.setFormateur(formateurIDAO.get(resultSet.getString("formateurID")));
        }
    }
}

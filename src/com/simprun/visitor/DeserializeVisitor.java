package com.simprun.visitor;

import com.simprun.dao.IDAO;
import com.simprun.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeserializeVisitor implements IDeserializeVisitor{
    private static DeserializeVisitor instance;
    private static IDAO<Apprenant> apprenantDriver;
    private static IDAO<Formateur> formateurDriver;
    private static IDAO<Brief> briefDriver;
    private static IDAO<Promo> promoDriver;
    private DeserializeVisitor() {}

    public static DeserializeVisitor getInstance() {
        if (instance == null) {
            instance = new DeserializeVisitor();
        }
        return instance;
    }

    public static void setDrivers(IDAO<Apprenant> apprenantDriver, IDAO<Formateur> formateurDriver, IDAO<Brief> briefDriver, IDAO<Promo> promoDriver) {
        DeserializeVisitor.apprenantDriver = apprenantDriver;
        DeserializeVisitor.formateurDriver = formateurDriver;
        DeserializeVisitor.briefDriver = briefDriver;
        DeserializeVisitor.promoDriver = promoDriver;
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
    }

    @Override
    public void visit(Formateur formateur, ResultSet resultSet) throws SQLException {
        setUserFields(formateur, resultSet);
        formateur.setPromo(promoDriver.get(resultSet.getString("promoID")));
    }

    @Override
    public void visit(Apprenant apprenant, ResultSet resultSet) throws SQLException {
        setUserFields(apprenant, resultSet);
        apprenant.setPromo(promoDriver.get(resultSet.getString("promoID")));
    }

    @Override
    public void visit(Brief brief, ResultSet resultSet) throws SQLException {
        brief.setId(resultSet.getString("id"));
        brief.setTitle(resultSet.getString("name"));
        brief.setDescription(resultSet.getString("description"));
        brief.setDeadline(resultSet.getDate("deadline"));
        brief.setStatus(BriefStatus.valueOf(resultSet.getString("status")));
        brief.setPromo(promoDriver.get(resultSet.getString("promoID")));
    }

    @Override
    public void visit(Deliverable deliverable, ResultSet resultSet) throws SQLException {
        deliverable.setId(resultSet.getString("id"));
        deliverable.setLink(resultSet.getString("link"));
        deliverable.setCreatedAt(resultSet.getDate("createdAt"));
        deliverable.setApprenant(apprenantDriver.get(resultSet.getString("apprenantID")));
        deliverable.setBrief(briefDriver.get(resultSet.getString("briefID")));
    }

    @Override
    public void visit(Promo promo, ResultSet resultSet) throws SQLException {
        promo.setId(resultSet.getString("id"));
        promo.setName(resultSet.getString("name"));
        promo.setYear(resultSet.getInt("year"));
        promo.setFormateur(formateurDriver.get(resultSet.getString("formateurID")));
    }
}

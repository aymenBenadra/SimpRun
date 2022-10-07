package com.simprun.visitor;

import com.simprun.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDeserializeVisitor {
    void visit(Admin admin, ResultSet resultSet) throws SQLException;

    void visit(Formateur formateur, ResultSet resultSet) throws SQLException;

    void visit(Apprenant apprenant, ResultSet resultSet) throws SQLException;

    void visit(Brief brief, ResultSet resultSet) throws SQLException;

    void visit(Deliverable deliverable, ResultSet resultSet) throws SQLException;

    void visit(Promo promo, ResultSet resultSet) throws SQLException;
}

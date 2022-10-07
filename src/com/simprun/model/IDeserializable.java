package com.simprun.model;

import com.simprun.visitor.IDeserializeVisitor;

import java.sql.ResultSet;

public interface IDeserializable {
    void accept(IDeserializeVisitor visitor, ResultSet resultSet);
}

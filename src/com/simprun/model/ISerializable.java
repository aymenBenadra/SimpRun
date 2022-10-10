package com.simprun.model;

import com.simprun.visitor.ISerializeVisitor;

import java.util.HashMap;

public interface ISerializable {
    HashMap<String, String> accept(ISerializeVisitor visitor);
}

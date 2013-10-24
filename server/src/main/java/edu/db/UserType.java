/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db;

import util.EnumConverter;

/**
 *
 * @author alexander
 */
public enum UserType implements EnumConverter {
    CUSTOMER(0), ADMIN(1), PUBLISHER(2);
    
    private final int id_;
    UserType(final int id) { id_ = id; }

    @Override
    public int convert() { return id_; }
}

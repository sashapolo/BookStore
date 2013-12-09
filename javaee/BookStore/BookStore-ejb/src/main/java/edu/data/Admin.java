/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "BusinessAdmin")
public class Admin extends User {
    private static final long serialVersionUID = 1L;
}

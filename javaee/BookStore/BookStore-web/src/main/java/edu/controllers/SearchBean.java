/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.enterprise.inject.Model;

/**
 *
 * @author alexander
 */
@Model
public class SearchBean {
    private String searchStr;

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }
    
    public String search() throws UnsupportedEncodingException {
        return "home?faces-redirect=true&search=" + URLEncoder.encode(searchStr, "UTF-8");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.converters;

import edu.data.Author;
import edu.ejb.AuthorEjb;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author alexander
 */
@Model
@FacesConverter(value = "edu.converters.AuthorConverter")
public class AuthorConverter implements Converter {
    @EJB
    private AuthorEjb ae;
    
    @Inject
    Logger logger;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        logger.log(Level.INFO, "Converting {0}", value);
        final Long id = Long.parseLong(value);
        return ae.findById(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        final Author author = (Author) value;
        return String.valueOf(author.getId());
    }
}

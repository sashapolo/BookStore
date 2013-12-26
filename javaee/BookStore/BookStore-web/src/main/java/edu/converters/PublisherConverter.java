/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.converters;

import edu.data.Publisher;
import edu.ejb.PublisherEjb;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author alexander
 */
@Model
@FacesConverter(value = "edu.converters.PublisherConverter")
public class PublisherConverter implements Converter {

    @EJB
    private PublisherEjb pe;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        final Long id = Long.parseLong(value);
        return pe.findById(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        final Publisher pub = (Publisher) value;
        return String.valueOf(pub.getId());
    }
    
}

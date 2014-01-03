/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author alexander
 */
@FacesConverter(forClass = Calendar.class)
public class CalendarConverter implements Converter {
    private final static SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        final Calendar c = Calendar.getInstance();
        try {
            c.setTime(SDF.parse(value));
        } catch (ParseException e) {
            final FacesMessage msg = new FacesMessage("Invalid date format");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        return c;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return SDF.format(((Calendar) value).getTime());
    }
}

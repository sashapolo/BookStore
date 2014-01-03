/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.validators;

import edu.data.User;
import edu.ejb.UserEjb;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author alexander
 */
@FacesValidator("edu.validators.LoginExistsValidator")
public class LoginExistsValidator implements Validator {
    @EJB
    private UserEjb ue;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        final User res = ue.findByLogin(value.toString());
        if (res != null) {
            final FacesMessage msg = new FacesMessage("Login already exists");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}

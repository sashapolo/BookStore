/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.validators;

import edu.data.User;
import edu.ejb.UserEjb;
import java.util.List;
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
@FacesValidator("edu.validators.LoginValidator")
public class LoginValidator implements Validator {
    @EJB
    private UserEjb ue;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        final List<User> res = ue.findByLogin(value.toString());
        if (res.isEmpty()) {
            final FacesMessage msg = new FacesMessage("Login does not exist");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}

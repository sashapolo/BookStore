/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.validators;

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
@FacesValidator("edu.validators.IsbnValidator")
public class IsbnValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        final String isbn = value.toString().replace("-", "");
        final FacesMessage msg = new FacesMessage("Invalid ISBN");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        
        final int length = isbn.length();
        if (length != 13 && length != 10) throw new ValidatorException(msg);

        int check = 0;
        final char[] digits = isbn.toCharArray();
        for (int i = 1; i < length; i += 2) {
            check += digits[i] - '0';
        }
        check *= 3;
        for (int i = 0; i < length; i += 2) {
            check += digits[i] - '0';
        }
        
        if (check % 10 != 0) throw new ValidatorException(msg);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author alexander
 */
public class IsbnValidator implements ConstraintValidator<IsbnAnno, String> {

    @Override
    public void initialize(IsbnAnno constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        
        final int length = value.length();
        if (length != 13 && length != 10) return false;

        int check = 0;
        final char[] digits = value.toCharArray();
        for (int i = 1; i < length; i += 2) {
            check += digits[i] - '0';
        }
        check *= 3;
        for (int i = 0; i < length; i += 2) {
            check += digits[i] - '0';
        }
        return check % 10 == 0;
    }
    
}

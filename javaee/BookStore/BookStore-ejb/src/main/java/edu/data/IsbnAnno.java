/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author alexander
 */
@Constraint(validatedBy = IsbnValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsbnAnno {
    String message() default "Invalid ISBN format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        IsbnAnno[] value();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.util;

import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 *
 * @author alexander
 */
public class LoggerProducer {
    
    @Produces
    public Logger createLogger(final InjectionPoint point) {
        return Logger.getLogger(point.getMember().getDeclaringClass().getName());
    }
}

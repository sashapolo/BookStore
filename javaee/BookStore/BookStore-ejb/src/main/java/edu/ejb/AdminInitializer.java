/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.Credentials;
import edu.data.User;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author alexander
 */
@Startup
@Singleton
public class AdminInitializer {  
    @EJB
    private UserEjb ue;
    
    @PostConstruct
    public void init() {
        if (!ue.findByLogin("admin").isEmpty()) return;
        
        final Credentials cred = new Credentials("admin", "admin");
        final int pass = "admin".hashCode();
        final User admin = new User.Builder().login("admin").password(pass).credentials(cred)
                .email("admin@admin.com").admin(true).build();
        ue.create(admin);
    }
}

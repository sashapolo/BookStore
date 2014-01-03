/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import edu.data.Publisher;
import edu.ejb.PublisherEjb;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author alexander
 */
@Model
public class PublisherController {
    @EJB
    private PublisherEjb pe;
    
    @Inject
    private Logger logger;
    
    private Publisher pub = new Publisher();
    
    public Publisher getPublisher() {
        return pub;
    }
    
    public List<Publisher> getPublisherList() {
        final List<Publisher> list = pe.findAll();
        logger.log(Level.INFO, "got list of size {0} from Publisher", list.size());
        return list;
    }
    
    public void createPublisher(final String name) {
        pub = new Publisher(name);
        logger.log(Level.INFO, "Creating publisher with name {0}", name);
        pub = pe.create(pub);
    }
    
    public List<Publisher> findByName(final String name) {
        return pe.findByName(name);
    }
    
    public void findById() {
        pub = pe.findById(pub.getId());
    }
    
    public void deletePublisher(final Publisher pub) {
        pe.delete(pub);
    }
    
    public String updatePublisher() {
        pe.update(pub);
        return "modify_publisher.xhtml?faces-redirect=true";
    }
}

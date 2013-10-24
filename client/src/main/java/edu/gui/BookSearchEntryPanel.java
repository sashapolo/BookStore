/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gui;

import business.Book;
import business.Customer;
import business.GoogleBook;
import edu.ServiceWrapper;
import exception.EntryNotFoundException;
import exception.ServiceException;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.BookStoreService;


/**
 *
 * @author alexander
 */
@SuppressWarnings("serial")
public class BookSearchEntryPanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(BookSearchEntryPanel.class.getName());

    /**
     * Creates new form BookSearchEntryPanel
     */
    public BookSearchEntryPanel(final Book book, final Customer user) {
        assert book != null;
        assert user != null;
        
        this.book = book;
        this.user = user;
        try {
            amount = service.getAmountOfBook(book);
        } catch (EntryNotFoundException | RemoteException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new IllegalStateException();
        }
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel bookNameLabel = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel isbnLabel = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel pubLabel = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        javax.swing.JLabel pubDateLabel = new javax.swing.JLabel();
        javax.swing.JButton buyButton = new javax.swing.JButton();
        javax.swing.JLabel priceLabel = new javax.swing.JLabel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel authorLabel = new javax.swing.JLabel();
        javax.swing.JLabel avaliableLabel = new javax.swing.JLabel();
        javax.swing.JButton viewGoogleButton = new javax.swing.JButton();

        bookNameLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        bookNameLabel.setText(book.getName());

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("ISBN:");

        isbnLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        isbnLabel.setText(book.getIsbn().toString());

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setText("Publisher:");

        pubLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        pubLabel.setText(book.getPublisher().getName());

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setText("Date:");

        pubDateLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        final GregorianCalendar date = book.getPublicationDate();
        final int year = date.get(Calendar.YEAR);
        final int month = date.get(Calendar.MONTH);
        final int day = date.get(Calendar.DAY_OF_MONTH);
        pubDateLabel.setText(year + "-" + month + '-' + day);

        buyButton.setText("Buy");
        if (amount == 0) {
            buyButton.setEnabled(false);
        }
        buyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyButtonActionPerformed(evt);
            }
        });

        priceLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        priceLabel.setText("$" + book.getPrice());

        jLabel1.setText("Author:");

        authorLabel.setText(book.getAuthor());

        avaliableLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        avaliableLabel.setForeground(new java.awt.Color(255, 0, 51));
        avaliableLabel.setText("Not avaliable");

        viewGoogleButton.setText("View on Google Books");
        viewGoogleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewGoogleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(bookNameLabel)
                                    .addGap(47, 153, Short.MAX_VALUE)
                                    .addComponent(priceLabel))
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel2))
                                    .addGap(33, 33, 33)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(isbnLabel)
                                            .addComponent(authorLabel)
                                            .addComponent(pubLabel)
                                            .addComponent(pubDateLabel))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(avaliableLabel)
                                                    .addComponent(buyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(viewGoogleButton, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookNameLabel)
                    .addComponent(priceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(authorLabel)
                    .addComponent(avaliableLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(isbnLabel)
                    .addComponent(buyButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(pubLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pubDateLabel)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(viewGoogleButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        if (amount != 0) {
            avaliableLabel.setVisible(false);
        }
    }// </editor-fold>//GEN-END:initComponents

    private void buyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyButtonActionPerformed
        final JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        parent.dispose();
        new BuyFrame(book, user).setVisible(true);
    }//GEN-LAST:event_buyButtonActionPerformed

    private void viewGoogleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewGoogleButtonActionPerformed
        final GoogleBook b;
        try {
            b = service.getGoogleBook(book.getIsbn());
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new IllegalStateException();
        }
        new GoogleBookFrame(b).setVisible(true);
    }//GEN-LAST:event_viewGoogleButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private final Book book;
    private final Customer user;
    private final int amount;
    private final BookStoreService service = ServiceWrapper.getBookStoreService();
}

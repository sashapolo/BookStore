/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gui;

import business.Book;
import edu.ServiceWrapper;
import exception.BookParseException;
import exception.EntryRedefinitionException;
import rmi.BookStoreService;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 */
@SuppressWarnings("serial")
public class NewBookFrame extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(NewBookFrame.class.getName());

    /**
     * Creates new form NewBookFrame
     */
    public NewBookFrame() {
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

        javax.swing.JButton backButton = new javax.swing.JButton();
        javax.swing.JButton createButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        createButton.setText("Create");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bookInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createButton)
                    .addComponent(backButton))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
    try {
            final String name = bookInfoPanel.getBookName();
            final String author = bookInfoPanel.getAuthor();
            final String genre = bookInfoPanel.getGenre();
            final String pubName = bookInfoPanel.getPub();
            final String isbn = bookInfoPanel.getIsbn();
            final String price = bookInfoPanel.getPrice();
            final String amount = bookInfoPanel.getAmount();
            final DateInputPanel panel = bookInfoPanel.getDateInputPanel();
            final GregorianCalendar date =
                    new GregorianCalendar(panel.getYear(), panel.getMonth(), panel.getDay());

            final BookStoreService service = ServiceWrapper.getBookStoreService();
            try {
                    final Book book = service.parseBook(name, author, genre, pubName, isbn, price, date, amount);
                    service.createBook(book, Integer.valueOf(amount));
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, null, e);
                    throw new IllegalStateException();
                }
        } catch (NumberFormatException | EntryRedefinitionException | BookParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Book created successfully", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_createButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        dispose();
        new MainAdminFrame().setVisible(true);
    }//GEN-LAST:event_backButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private final edu.gui.BookInfoPanel bookInfoPanel = new edu.gui.BookInfoPanel();
    // End of variables declaration//GEN-END:variables
}

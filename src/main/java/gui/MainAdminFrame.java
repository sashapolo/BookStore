/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import business.Book;
import business.WrongFormatException;
import javax.swing.JOptionPane;
import service.BookCatalogue;
import service.PublisherCatalogue;

/**
 *
 * @author alexander
 */
@SuppressWarnings("serial")
public class MainAdminFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainAdminFrame
     */
    public MainAdminFrame() {
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

        javax.swing.JPanel bookPanel = new javax.swing.JPanel();
        javax.swing.JButton addBookButton = new javax.swing.JButton();
        javax.swing.JButton modifyBookButton = new javax.swing.JButton();
        javax.swing.JPanel pubPanel = new javax.swing.JPanel();
        javax.swing.JButton addPubButton = new javax.swing.JButton();
        javax.swing.JButton modifyPubButton = new javax.swing.JButton();
        javax.swing.JButton exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bookPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Books"));

        addBookButton.setText("Create book");
        addBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookButtonActionPerformed(evt);
            }
        });

        modifyBookButton.setText("Modify book");
        modifyBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyBookButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bookPanelLayout = new javax.swing.GroupLayout(bookPanel);
        bookPanel.setLayout(bookPanelLayout);
        bookPanelLayout.setHorizontalGroup(
            bookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(modifyBookButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addBookButton, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bookPanelLayout.setVerticalGroup(
            bookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addBookButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modifyBookButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pubPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Publishers"));

        addPubButton.setText("Add publisher");
        addPubButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPubButtonActionPerformed(evt);
            }
        });

        modifyPubButton.setText("Modify publisher");
        modifyPubButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyPubButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pubPanelLayout = new javax.swing.GroupLayout(pubPanel);
        pubPanel.setLayout(pubPanelLayout);
        pubPanelLayout.setHorizontalGroup(
            pubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(modifyPubButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addPubButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pubPanelLayout.setVerticalGroup(
            pubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPubButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modifyPubButton)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        exitButton.setText("Log out");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bookPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pubPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(exitButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pubPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bookPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitButton)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        dispose();
        new AuthFrame().setVisible(true);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void addBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookButtonActionPerformed
        dispose();
        new NewBookFrame().setVisible(true);
    }//GEN-LAST:event_addBookButtonActionPerformed

    private void modifyBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyBookButtonActionPerformed
        final String isbn = JOptionPane.showInputDialog(this, "Type book isbn");
        try {
            final Book book = BookCatalogue.getBook(isbn);
            dispose();
            new ModifyBookFrame(book).setVisible(true);
        } catch (WrongFormatException ex) {
            JOptionPane.showMessageDialog(this, "Wrong isbn format", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_modifyBookButtonActionPerformed

    private void modifyPubButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyPubButtonActionPerformed
        dispose();
        new PublisherChooserFrame().setVisible(true);
    }//GEN-LAST:event_modifyPubButtonActionPerformed

    private void addPubButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPubButtonActionPerformed
        final String name = JOptionPane.showInputDialog(this, "Input publisher's name");
        if (name != null) {
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name field is empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PublisherCatalogue.createPublisher(name);
            JOptionPane.showMessageDialog(this, "Publisher created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_addPubButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

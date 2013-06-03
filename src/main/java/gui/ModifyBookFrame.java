/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import business.Book;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import service.BookCatalogue;
import service.BookInfoParser;
import service.BookParseException;
import service.EntryNotFoundException;

/**
 *
 * @author alexander
 */
@SuppressWarnings("serial")
public class ModifyBookFrame extends javax.swing.JFrame {

    /**
     * Creates new form ModifyBookFrame
     */
    public ModifyBookFrame(final Book book) {
        assert (book != null);
        book_ = book;
        initComponents();
        bookInfoPanel.setBookName(book.getName());
        bookInfoPanel.setAuthor(book.getAuthor());
        bookInfoPanel.setIsbn(book.getIsbn().toString());
        bookInfoPanel.setGenre(book.getGenre());
        bookInfoPanel.setPrice(String.valueOf(book.getPrice()));
        bookInfoPanel.setPub(book.getPublisher().getName());
        final GregorianCalendar date= book.getPublicationDate();
        bookInfoPanel.setDate(date.get(Calendar.DAY_OF_WEEK), 
                              date.get(Calendar.MONTH), 
                              date.get(Calendar.YEAR));
        try {
            bookInfoPanel.setAmount(String.valueOf(BookCatalogue.getAmount(book)));
        } catch (EntryNotFoundException ex) {
            // unreachable
            assert false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bookInfoPanel = new gui.BookInfoPanel();
        javax.swing.JButton modifyButton = new javax.swing.JButton();
        javax.swing.JButton deleteButton = new javax.swing.JButton();
        javax.swing.JButton backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bookInfoPanel.setName("bookInfoPanel"); // NOI18N

        modifyButton.setText("Modify");
        modifyButton.setName("modifyButton"); // NOI18N
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.setName("deleteButton"); // NOI18N
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        backButton.setText("back");
        backButton.setName("backButton"); // NOI18N
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modifyButton)
                    .addComponent(deleteButton)
                    .addComponent(backButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bookInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(modifyButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(backButton)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
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

            final Book book = BookInfoParser.parseBook(name, 
                                                       author, 
                                                       genre, 
                                                       pubName, 
                                                       isbn, 
                                                       price, 
                                                       date, 
                                                       amount);
            book.setId(book_.getId());
            BookCatalogue.updateBook(book);
            BookCatalogue.setAmount(book, Integer.valueOf(amount));
        } catch (NumberFormatException | BookParseException | EntryNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Book updated successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_modifyButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        BookCatalogue.deleteBook(book_);
        JOptionPane.showMessageDialog(this, "Book deleted successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new MainAdminFrame().setVisible(true);
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        dispose();
        new MainAdminFrame().setVisible(true);
    }//GEN-LAST:event_backButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.BookInfoPanel bookInfoPanel;
    // End of variables declaration//GEN-END:variables
    private final Book book_;
}

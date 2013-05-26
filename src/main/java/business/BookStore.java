package business;

import gui.AuthForm;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/26/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookStore {
    public static void main(String [] args) {
        final JFrame frame = new JFrame("Book store");
        frame.setContentPane(new AuthForm().getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

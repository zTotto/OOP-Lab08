package it.unibo.oop.lab.mvcio2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface. Suggestion: use a second JPanel with a second
     * BorderLayout, put the panel in the North of the main panel, put the text
     * field in the center of the new panel and put the button in the line_end of
     * the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the current
     * selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should use
     * the method showSaveDialog() to display the file chooser, and if the result is
     * equal to JFileChooser.APPROVE_OPTION the program should set as new file in
     * the Controller the file chosen. If CANCEL_OPTION is returned, then the
     * program should do nothing. Otherwise, a message dialog should be shown
     * telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to update
     * the UI: in this example the UI knows when should be updated, so try to keep
     * things separated.
     */
    private final JFrame frame = new JFrame(TITLE);
    private static final String TITLE = "My first Java graphical interface";

    /**
     * Builds a new {@link SimpleGUIWithFileChooser}.
     * 
     * @param cont
     */
    public SimpleGUIWithFileChooser(final Controller cont) {
        //Panels
        final JPanel panBrowse = new JPanel();
        final JPanel panText = new JPanel();
        panText.setLayout(new BorderLayout());
        panBrowse.setLayout(new BorderLayout());
        //String saver
        final JTextArea area = new JTextArea();
        final JButton save = new JButton("Save");
        //File chooser
        final JTextField pathField = new JTextField();
        final JButton browser = new JButton("Browse files");
        final JFileChooser chooser = new JFileChooser("Choose file where to write");
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //Layout
        panText.add(area, BorderLayout.CENTER);
        panText.add(save, BorderLayout.SOUTH);
        panBrowse.add(pathField, BorderLayout.CENTER);
        panBrowse.add(browser, BorderLayout.LINE_END);
        panText.add(panBrowse, BorderLayout.NORTH);
        //Functions
        pathField.setEditable(false);
        save.addActionListener(new ActionListener() {   //Save button action

            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    cont.writeOnFile(area.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        browser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final int returnVal = chooser.showSaveDialog(frame);
                switch (returnVal) {
                case JFileChooser.CANCEL_OPTION:
                    break;
                case JFileChooser.APPROVE_OPTION:
                    cont.setFile(chooser.getSelectedFile());
                    pathField.setText(cont.getPath());
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, returnVal, "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        });
        //Base operations
        frame.setContentPane(panText);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        final Dimension chooserSize = screen;
        chooserSize.setSize(screen.getWidth() / 3, screen.getHeight() / 3);
        chooser.setPreferredSize(chooserSize);
        frame.setSize(sw / 3, sh / 3);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void display() {
        frame.setVisible(true);
    }

    /**
     * @param args
     *                 ignored
     */
    public static void main(final String... args) {
        new SimpleGUIWithFileChooser(new Controller()).display();
    }
}

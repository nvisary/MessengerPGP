package com.summerproject.messenger.ui;

import com.summerproject.messenger.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PasswordScreen extends JDialog {
    protected Font font13 = new Font("Roboto", Font.PLAIN, 15);

    private JButton btnAccept;
    private JLabel label;
    private JPasswordField passwordField;
    private JLabel labelHelp;
    private Model model;


    public PasswordScreen(JFrame parentFrame, String title, Model model) {
        super(parentFrame, title, true);
        this.model = model;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 350, 150);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                saveAndExit();
            }
        });

        btnAccept = new JButton("Accept");
        btnAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndExit();
            }
        });
        btnAccept.setFont(font13);
        btnAccept.setBounds(105, 55, 90, 40);

        label = new JLabel("Input password:");
        label.setFont(font13);
        label.setBounds(10, 15, 200, 50);

        passwordField = new JPasswordField();

        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndExit();
            }
        });
        passwordField.setFont(font13);
        passwordField.setBounds(122, 27, 200, 25);

        labelHelp = new JLabel("Please, input password...");
        labelHelp.setFont(font13);
        labelHelp.setForeground(Color.red);
        labelHelp.setBounds(122, 10, 200, 15);
        labelHelp.setVisible(false);
        setLayout(null);

        add(label);
        add(btnAccept);
        add(passwordField);
        add(labelHelp);
    }

    private void saveAndExit() {
        String password = String.valueOf(passwordField.getPassword());
        if (!password.equals("")) {
            model.setUserPassword(password);
            dispose();
        } else {
            labelHelp.setVisible(true);
        }
    }
}

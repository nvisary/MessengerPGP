package com.summerproject.messenger.ui;

import com.summerproject.messenger.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PasswordScreen extends JDialog {
    protected Font font13 = new Font("Roboto", Font.PLAIN, 15);

    private JButton btnAccept;
    private JLabel lblInputPassword;
    private JPasswordField jpfPassword;
    private JLabel lblHelp;
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

        lblInputPassword = new JLabel("Input password:");
        lblInputPassword.setFont(font13);
        lblInputPassword.setBounds(10, 15, 200, 50);

        jpfPassword = new JPasswordField();
        jpfPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndExit();
            }
        });
        jpfPassword.setFont(font13);
        jpfPassword.setBounds(122, 27, 200, 25);

        lblHelp = new JLabel("Please, input password...");
        lblHelp.setFont(font13);
        lblHelp.setForeground(Color.red);
        lblHelp.setBounds(122, 10, 200, 15);
        lblHelp.setVisible(false);
        setLayout(null);

        add(lblInputPassword);
        add(btnAccept);
        add(jpfPassword);
        add(lblHelp);
    }

    private void saveAndExit() {
        String password = String.valueOf(jpfPassword.getPassword());
        if (!password.equals("")) {
            model.setUserPassword(password);
            dispose();
        } else {
            lblHelp.setVisible(true);
        }
    }
}

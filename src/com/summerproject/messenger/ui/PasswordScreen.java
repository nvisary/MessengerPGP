package com.summerproject.messenger.ui;

import com.summerproject.messenger.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PasswordScreen extends JDialog implements ActionListener {
    protected Font font15 = new Font("Roboto", Font.PLAIN, 15);

    private JButton btnAccept;
    private JLabel lblInputPassword;
    private JPasswordField jpfPassword;
    private JLabel lblHelp;
    private JLabel lblPort;
    private JTextField jtfPort;
    private Model model;


    public PasswordScreen(JFrame parentFrame, String title, Model model) {
        super(parentFrame, title, true);
        this.model = model;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 350, 200);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                saveAndExit();
            }
        });

        btnAccept = new JButton("Accept");
        btnAccept.addActionListener(this);
        btnAccept.setFont(font15);
        btnAccept.setBounds(105, 105, 90, 40);

        lblInputPassword = new JLabel("Input password:");
        lblInputPassword.setFont(font15);
        lblInputPassword.setBounds(10, 15, 200, 50);

        jpfPassword = new JPasswordField();
        jpfPassword.addActionListener(this);
        jpfPassword.setFont(font15);
        jpfPassword.setBounds(122, 27, 200, 25);

        lblHelp = new JLabel("Please, input password...");
        lblHelp.setFont(font15);
        lblHelp.setForeground(Color.red);
        lblHelp.setBounds(122, 10, 200, 15);
        lblHelp.setVisible(false);
        setLayout(null);

        lblPort = new JLabel("Port: ");
        lblPort.setFont(font15);
        lblPort.setBounds(10, 42, 200, 50);

        jtfPort = new JTextField("7777");
        jtfPort.setFont(font15);
        jtfPort.setBounds(122, 55, 80, 25);

        add(jtfPort);
        add(lblPort);
        add(lblInputPassword);
        add(btnAccept);
        add(jpfPassword);
        add(lblHelp);
    }

    private void saveAndExit() {
        String password = String.valueOf(jpfPassword.getPassword());
        if (!password.equals("")) {
            model.setUserPassword(password);
            model.setServerPort(jtfPort.getText());
            dispose();
        } else {
            lblHelp.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveAndExit();
    }
}

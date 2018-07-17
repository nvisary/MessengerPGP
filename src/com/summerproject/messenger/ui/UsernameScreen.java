package com.summerproject.messenger.ui;

import com.summerproject.messenger.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UsernameScreen extends JDialog implements ActionListener {
    protected Font font15 = new Font("Roboto", Font.PLAIN, 15);
    private Model model;
    private JTextField jtfUsername;
    private JLabel lblUsername;
    private JButton btnAccept;
    private JLabel lblHelp;

    public UsernameScreen(JFrame parentFrame, String title, Model model) {
        super(parentFrame, title, true);
        this.model = model;
        setBounds(100, 100, 350, 150);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveAndExit();
            }
        });


        lblUsername = new JLabel("Input username: ");
        lblUsername.setFont(font15);
        lblUsername.setBounds(10, 15, 160, 50);

        jtfUsername = new JTextField();
        jtfUsername.setFont(font15);
        jtfUsername.setBounds(122, 27, 200, 25);
        jtfUsername.addActionListener(this);

        btnAccept = new JButton("Accept");
        btnAccept.setFont(font15);
        btnAccept.setBounds(105, 55, 90, 40);
        btnAccept.addActionListener(this);

        lblHelp = new JLabel("Please, input username...");
        lblHelp.setFont(font15);
        lblHelp.setForeground(Color.red);
        lblHelp.setVisible(false);
        lblHelp.setBounds(122, 10, 200, 15);

        add(lblHelp);
        add(lblUsername);
        add(jtfUsername);
        add(btnAccept);
    }

    public static void main(String[] args) {
        UsernameScreen screen = new UsernameScreen(null, "TEST", null);
        screen.setVisible(true);
    }

    private void saveAndExit() {
        String username = jtfUsername.getText();
        if (!username.equals("")) {
            model.setUsername(jtfUsername.getText());
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

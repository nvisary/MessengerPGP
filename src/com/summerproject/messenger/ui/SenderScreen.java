package com.summerproject.messenger.ui;

import com.summerproject.messenger.net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SenderScreen extends Screen {

    private JTextField textField;
    private JButton button;
    private Client client;
    public SenderScreen(String title) {
        super(title);
        setBounds(100, 100, 500, 600);

        textField = new JTextField();
        button = new JButton("Send");
        client = new Client("localhost", 7777);


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.send(textField.getText());
                textField.setText("");
            }
        });

        add(textField, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SenderScreen senderScreen = new SenderScreen("Send");
        senderScreen.display();
    }
}


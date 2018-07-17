package com.summerproject.messenger.ui;

import com.summerproject.messenger.model.Model;

import javax.swing.*;
import java.awt.*;

public class DialogAndSendScreen extends Screen {

    private JLabel lblReceiverIp;
    private JLabel lblReceiverPort;
    private JLabel lblReceiverPublicKey;

    private JTextField jtfReceiverIp;
    private JTextField jtfReceiverPort;
    private JTextField jtfReceiverPublicKey;

    private JList<String> jlDialog;
    private JButton btnSend;

    private DefaultListModel<String> listModel;
    private Model model;


    public DialogAndSendScreen(String title, Model model) {
        super(title);
        setBounds(100, 100, 500, 600);
        setLayout(null);
        this.model = model;

        lblReceiverIp = new JLabel("Receiver ip: ");
        lblReceiverIp.setBounds(10, 10, 100, 20);
        lblReceiverIp.setFont(font15);


        jtfReceiverIp = new JFormattedTextField();
        jtfReceiverIp.setBounds(100, 9, 120, 25);
        jtfReceiverIp.setText("127.0.0.1");
        jtfReceiverIp.setFont(font15);

        lblReceiverPort = new JLabel("Receiver port: ");
        lblReceiverPort.setFont(font15);
        lblReceiverPort.setBounds(250, 10, 100, 20);

        jtfReceiverPort =  new JTextField("7777");
        jtfReceiverPort.setFont(font15);
        jtfReceiverPort.setBounds(350, 9, 100, 25);

        lblReceiverPublicKey = new JLabel("Receiver public key: ");
        lblReceiverPublicKey.setBounds(10, 40, 150, 20);
        lblReceiverPublicKey.setFont(font15);

        jtfReceiverPublicKey = new JTextField();
        jtfReceiverPublicKey.setBounds(10, 65, 470, 25);
        jtfReceiverPublicKey.setFont(font15);

        listModel = new DefaultListModel<>();
        jlDialog = new JList<>(listModel);
        jlDialog.setFont(font15);
        jlDialog.setBackground(Color.white);
        jlDialog.setBounds(10, 110, 470, 400);

        btnSend = new JButton("Send");
        btnSend.setFont(font15);
        btnSend.setBounds(10, 515, 100, 20);

        add(btnSend);
        add(jlDialog);
        add(jtfReceiverPublicKey);
        add(lblReceiverPublicKey);
        add(jtfReceiverPort);
        add(lblReceiverPort);
        add(jtfReceiverIp);
        add(lblReceiverIp);

    }

    public void addToList(String name, String message) {

    }

    public static void main(String[] args) {
        DialogAndSendScreen dialogAndSendScreen = new DialogAndSendScreen("TEST",null);
        dialogAndSendScreen.display();
    }
}

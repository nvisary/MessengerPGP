package com.summerproject.messenger.ui;

import com.summerproject.messenger.model.Model;
import com.summerproject.messenger.net.Client;
import com.summerproject.messenger.pgp.rsa.PublicKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogAndSendScreen extends Screen implements ActionListener {

    private JLabel lblReceiverIp;
    private JLabel lblReceiverPort;
    private JLabel lblReceiverPublicKey;

    private JTextField jtfReceiverIp;
    private JTextField jtfReceiverPort;
    private JTextField jtfReceiverPublicKey;

    private JList<String> jlDialog;
    private JButton btnSend;
    private JTextField jtfYourMessage;

    private DefaultListModel<String> listModel;
    private Model model;


    public DialogAndSendScreen(String title, Model model) {
        super(title);
        setBounds(100, 100, 500, 600);
        setLayout(null);
        this.model = model;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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

        jtfReceiverPort = new JTextField("7777");
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
        jlDialog.setBounds(10, 110, 470, 350);

        jtfYourMessage = new JTextField();
        jtfYourMessage.setBounds(10, 470, 470, 25);
        jtfYourMessage.setFont(font15);

        btnSend = new JButton("Send");
        btnSend.setFont(font15);
        btnSend.setBounds(10, 500, 100, 20);
        btnSend.addActionListener(this);

        add(jtfYourMessage);
        add(btnSend);
        add(jlDialog);
        add(jtfReceiverPublicKey);
        add(lblReceiverPublicKey);
        add(jtfReceiverPort);
        add(lblReceiverPort);
        add(jtfReceiverIp);
        add(lblReceiverIp);
    }

    public static void main(String[] args) {
        DialogAndSendScreen dialogAndSendScreen = new DialogAndSendScreen("TEST", null);
        dialogAndSendScreen.display();
    }

    public void addToList(String name, String message) {
        String pattern1 = "[You] %s";
        if (name.equals("You")) {
            listModel.addElement(String.format(pattern1, message));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int port = Integer.parseInt(jtfReceiverPort.getText());
        String ip = jtfReceiverIp.getText();
        String message = jtfYourMessage.getText();
        System.out.println(ip + ":" + port);
        System.out.println(message);
        if (!message.equals("")) {
            addToList("You", message);
            Client client = new Client(ip, port);
            client.send(message);
        }

        if (!jtfReceiverPublicKey.getText().equals("")) {
            PublicKey publicKey = new PublicKey(jtfReceiverPublicKey.getText());
            System.out.println(publicKey);
        }
    }
}

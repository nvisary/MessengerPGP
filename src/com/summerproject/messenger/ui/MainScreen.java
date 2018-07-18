package com.summerproject.messenger.ui;

import com.summerproject.messenger.model.Model;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.awt.*;

public class MainScreen extends Screen {
    private Model model;
    private JList<String> jlYourMessages;
    private JLabel lblYourMessages;
    private DefaultListModel<String> listModel;
    private Map<String, Integer> map = new HashMap<>();
    private JTextField jtfPublicKey;
    private JLabel lblPublicKey;
    private JLabel lblYourServerIp;
    private JLabel lblYourServerPort;
    private JTextField jtfYourServerIp;
    private JTextField jtfYourServerPort;
    private JButton btnOpenSendMessageScreen;

    public MainScreen(String title, Model model){
        super(title);
        this.model = model;
        setBounds(100, 100, 500, 600);
        setLayout(null);

        listModel = new DefaultListModel<>();

        lblPublicKey = new JLabel("Your public key:");
        lblPublicKey.setFont(font15);
        lblPublicKey.setBounds(10, 10, 150, 20);

        jtfPublicKey = new JTextField();
        jtfPublicKey.setEditable(false);
        jtfPublicKey.setFont(font15);
        jtfPublicKey.setBounds(10, 35, 470, 25);

        lblYourServerIp = new JLabel("Your ip: ");
        lblYourServerIp.setBounds(10, 65, 100, 25);
        lblYourServerIp.setFont(font15);

        lblYourServerPort = new JLabel("Your port: ");
        lblYourServerPort.setBounds(170, 65, 100, 25);
        lblYourServerPort.setFont(font15);

        String ip = null;
        try {
            ip = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ip = ip.substring(ip.indexOf("/") + 1, ip.length());

        jtfYourServerIp = new JTextField(ip);
        jtfYourServerIp.setEditable(false);
        jtfYourServerIp.setFont(font15);
        jtfYourServerIp.setBounds(65, 68, 100, 20);

        jtfYourServerPort = new JTextField();
        jtfYourServerPort.setEditable(false);
        jtfYourServerPort.setFont(font15);
        jtfYourServerPort.setBounds(240, 68, 45, 20);
        jtfYourServerPort.setText(String.valueOf(model.getServerPort()));

        lblYourMessages = new JLabel("Messages: ");
        lblYourMessages.setFont(font15);
        lblYourMessages.setBounds(10, 90, 160, 25);


        jlYourMessages = new JList<String>(listModel);
        jlYourMessages.setBounds(10, 120, 470, 400);
        jlYourMessages.setBackground(Color.white);
        jlYourMessages.setFont(font15);
        jlYourMessages.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() && !jlYourMessages.isSelectionEmpty()) {
                    String str = jlYourMessages.getSelectedValue();
                    int id1 = str.indexOf("[");
                    int id2 = str.indexOf("]");
                    String username = str.substring(id1 + 1, id2 );
                    DialogAndSendScreen dialogAndSendScreen = new DialogAndSendScreen("Send to " + username, model, username);
                    dialogAndSendScreen.display();

                }

            }
        });

        /*jlYourMessages.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DialogAndSendScreen screen = new DialogAndSendScreen("Send to", model, "You");
                    screen.display();
                }
            }
        });*/

        btnOpenSendMessageScreen = new JButton("Send message...");
        btnOpenSendMessageScreen.setFont(font15);
        btnOpenSendMessageScreen.setBounds(getWidth() - 200, 530, 150, 20);
        btnOpenSendMessageScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogAndSendScreen dialogAndSendScreen = new DialogAndSendScreen("Send to...", model, "You");
                dialogAndSendScreen.display();
            }
        });

        add(btnOpenSendMessageScreen);
        add(jlYourMessages);
        add(lblYourMessages);
        add(jtfYourServerPort);
        add(jtfYourServerIp);
        add(lblYourServerPort);
        add(lblYourServerIp);
        add(jtfPublicKey);
        add(lblPublicKey);
    }

    public void addToList(String name, String message) {
        String pattern = "[%s] | %s";
        if (!map.containsKey(name)) {
            listModel.addElement(String.format(pattern, name, message));
            int id = listModel.indexOf(String.format(pattern, name, message));
            map.put(name, id);
        } else {
            int id = map.get(name);
            listModel.setElementAt(String.format(pattern, name, message), id);
        }
    }

    public void setPort(int serverPort) {
        jtfYourServerPort.setText(String.valueOf(serverPort));
    }

    public void setTextToJTFPublicKey(String key) {
        jtfPublicKey.setText(key);
    }

    public static void main(String[] args)  {
        MainScreen screen = new MainScreen("Test", null);
        screen.display();

    }


}

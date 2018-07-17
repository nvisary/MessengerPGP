package com.summerproject.messenger.ui;

import com.summerproject.messenger.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MainScreen extends Screen {
    private Model model;
    private JList<String> listMessages;
    private DefaultListModel<String> listModel;
    private Map<String, Integer> map = new HashMap<>();
    private JButton button;
    private JTextField publicKeyTextField;

    public MainScreen(String title, Model model) {
        super(title);
        this.model = model;
        setBounds(100, 100, 500, 600);
        setLayout(null);
        listModel = new DefaultListModel<>();

        listMessages = new JList<String>(listModel);
        listMessages.setBounds(10, 10, 100, 200);
        listMessages.setBackground(Color.yellow);

        button = new JButton("Add");
        button.setBounds(100, 50, 100, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.addElement("Hello");
            }
        });

        add(button);
        add(listMessages);
    }

    public void addToList(String name, String message) {
        if (!map.containsKey(name)) {
            listModel.addElement("[" + name + "]   " + message);
            int id = listModel.indexOf("[" + name + "]   " + message);
            map.put(name, id);
        } else {
            int id = map.get(name);
            listModel.setElementAt("[" + name + "]   " + message, id);
        }
    }





}

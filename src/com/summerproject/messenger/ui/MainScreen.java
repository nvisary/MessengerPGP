package com.summerproject.messenger.ui;

import com.summerproject.messenger.model.Model;

import javax.swing.*;

public class MainScreen extends Screen {
    private Model model;
    private JLabel label;

    public MainScreen(String title, Model model) {
        super(title);
        this.model = model;
        setBounds(100, 100, 500, 600);

        label = new JLabel("ad");
        add(label);
    }

}

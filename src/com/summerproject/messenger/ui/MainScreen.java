package com.summerproject.messenger.ui;

import com.summerproject.messenger.model.Model;

import javax.swing.*;

public class MainScreen extends Screen {
    private Model model;
    public JLabel label;
    public JLabel label2;

    public MainScreen(String title, Model model) {
        super(title);
        this.model = model;
        setBounds(100, 100, 500, 600);

    }



}

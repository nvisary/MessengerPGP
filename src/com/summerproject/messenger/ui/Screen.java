package com.summerproject.messenger.ui;


import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JFrame {
    protected Font font15 = new Font("Roboto", Font.PLAIN, 15);
    protected Font font17 = new Font("Roboto", Font.PLAIN, 17);
    public Screen(String title) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void display() {
        setVisible(true);
    }


}

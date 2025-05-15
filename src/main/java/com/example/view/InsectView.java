package com.example.view;

import com.example.model.Insect;

import javax.swing.*;
import java.awt.*;

public class InsectView extends JPanel {
    private Insect insect;
    private Color color;

    public InsectView(Insect insect) {
        this.insect = insect;
        this.color = Color.decode(insect.getColor());
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Implement the drawing logic for the mycelium here
        // For example, you might use a graphics library to draw the mycelium shape
        // at the specified position with the given width, height, and rotation.
        ;
    }
}

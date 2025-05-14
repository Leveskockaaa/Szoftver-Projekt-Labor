package com.example.view;

import java.awt.Canvas;
import java.awt.Graphics;

import javax.swing.JFrame;

public class FungoriumCanvas {
    private static final JFrame frame;
    private static final Canvas canvas;

    private FungoriumCanvas() {
        // Private constructor to prevent instantiation
    }

    static {
        frame = new JFrame("Drawing Canvas");
        canvas = new Canvas();
        canvas.setSize(800, 600);
        frame.add(canvas);
        frame.pack();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static Graphics getGraphics() {
        return canvas.getGraphics();
    }
}
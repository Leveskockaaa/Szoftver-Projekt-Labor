package com.example.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.example.Timer;

public class GamePanel extends JPanel {
    private static ArrayList<Drawable> alakzatok = new ArrayList<>();
    private TectonView selectedTecton = null;

    public GamePanel() {
        // Egér események kezelése
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedTecton = chooseTecton(e.getX(), e.getY());
                System.out.println("Clicked at: " + e.getX() + ", " + e.getY());
                System.out.println("Selected Tecton: " + selectedTecton);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Háttér
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (Drawable drawable : alakzatok) {
            drawable.draw(1.0f, g2d);
        }
        if (selectedTecton != null) {
            SporesView sporesView = new SporesView(selectedTecton.getTecton().getSpores());
            //sporesView.draw(1.0f, g2d);
            Timer timer = new Timer(5, () -> {
                selectedTecton = null;
                repaint();
            });
        }
    }

    private TectonView chooseTecton(int x, int y) {
        for (Drawable drawable : alakzatok) {
            if (drawable instanceof TectonView) {
                TectonView tecton = (TectonView) drawable;
                if (tecton.isSelected(x, y)) {
                    selectedTecton = tecton;
                    return selectedTecton;
                }
            }
        }
        return null;
    }

    public void add(Drawable component) {
        alakzatok.add(component);
        repaint();
    }
}

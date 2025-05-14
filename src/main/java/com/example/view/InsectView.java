package com.example.view;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.model.Insect;

public class InsectView implements Drawable {
    private Insect insect;
    private final Color color;

    public InsectView(Insect insect) {
        this.insect = insect;
        this.color = Color.decode(insect.getColor());
    }

    @Override
    public void draw(Position position, float scale) {
        Graphics2D g2d = (Graphics2D) FungoriumCanvas.getGraphics();
        int ovalWidth = (int)(position.width * scale);
        int ovalHeight = (int)(position.height * scale);
        g2d.setColor(color);
        g2d.fillOval(position.x, position.y, ovalWidth, ovalHeight);
    }
}

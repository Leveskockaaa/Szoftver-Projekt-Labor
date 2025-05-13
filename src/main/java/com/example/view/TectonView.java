package com.example.view;

import com.example.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.*;
import java.util.List;

//class DividedCircles extends JPanel {
//    private static final int CIRCLE_DIAMETER = 100;
//    private int x;
//    private int y;
//
//    public DividedCircles(int x, int y, List<Spore> spores, List<Insect> insects, MushroomBody mushroomBody) {
//        this.x = x;
//        this.y = y;
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//
//
//            // Draw the circle outline
//            g2d.setColor(Color.BLACK);
//            g2d.drawOval(x, y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
//
//        // Calculate the center of the circle
//        int circleCenterX = x + CIRCLE_DIAMETER / 2;
//        int circleCenterY = y + CIRCLE_DIAMETER / 2;
//
//        // Draw the radial lines from the specified point to the circle's edge
//        for (int angle = 30; angle < 390; angle += 120) {
//            double radian = Math.toRadians(angle);
//            int endX = (int) (circleCenterX + (CIRCLE_DIAMETER / 2) * Math.cos(radian));
//            int endY = (int) (circleCenterY + (CIRCLE_DIAMETER / 2) * Math.sin(radian));
//            g2d.drawLine(circleCenterX, circleCenterY, endX, endY);
//        }
//
//        // Draw the circle outline again to cover sector edges
//        g2d.setColor(Color.BLACK);
//        g2d.drawOval(x, y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
//    }
//}

public class TectonView extends JPanel implements Drawable {
    private Tecton tecton;
    private Color color;
    private float scale = 1.0f;
    private int radius;
    private MushroomBodyView mushroomBodyView;
    private List<InsectView> insectViews = new ArrayList<>();
    private List<MyceliumView> myceliumViews = new ArrayList<>();
    private HashMap<TectonView, Boolean> neighbors = new HashMap<>();
    private SporesView sporeViews;
    private Position position;
    private JFrame frame;



    public TectonView(Tecton tecton) {
        this.tecton = tecton;
        System.out.println(tecton.printType());
        switch (tecton.printType()) {
            case "Transix":
                color = new Color(0xFE9C9D);
                break;
            case "Magmox":
                color = new Color(0xBFFE9D);
                break;
            case "Mantleon":
                color = new Color(0x9EECFF);
                break;
            case "Orogenix":
                color = new Color(0xDF9DFE);
                break;
        }
        switch (tecton.getSize()){
            case SMALL:
                radius = 50;
                break;
            case MEDIUM:
                radius = 100;
                break;
            case BIG:
                radius = 150;
                break;
            case GIANT:
                radius = 200;
                break;
        }
        System.out.println(tecton.printType());
        System.out.println(tecton.getMushroomBody().printType());
        System.out.println(color);
        System.out.println(tecton.getSize());
        System.out.println(radius);
        //mushroomBodyView = new MushroomBodyView(tecton.getMushroomBody());
    }

    @Override
    public void draw(Position position, float scale, JFrame frame) {
        this.frame = frame;
        this.position = position;
        this.scale = scale;

        frame.add(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



        // Draw the circle outline
        g2d.setColor(Color.BLACK);
        g2d.drawOval(position.x, position.y, radius, radius);
        g2d.setColor(color);
        g2d.fillOval(position.x, position.y, radius, radius);

        // Calculate the center of the circle
        int circleCenterX = position.x + radius / 2;
        int circleCenterY = position.y + radius / 2;

        // Draw the radial lines from the specified point to the circle's edge
        g2d.setColor(Color.BLACK);
        for (int angle = 30; angle < 390; angle += 120) {
            double radian = Math.toRadians(angle);
            int endX = (int) (circleCenterX + (radius / 2) * Math.cos(radian));
            int endY = (int) (circleCenterY + (radius / 2) * Math.sin(radian));
            g2d.drawLine(circleCenterX, circleCenterY, endX, endY);
        }

        // Draw the circle outline again to cover sector edges
        g2d.setColor(Color.BLACK);
        g2d.drawOval(position.x, position.y, radius, radius);

        Color mushroomBodyColor;
        MushroomBody mushroomBody = tecton.getMushroomBody();
        if (mushroomBody instanceof Hyphara) {
            mushroomBodyColor = new Color(0xFF0000);
        } else if (mushroomBody instanceof Gilledon) {
            mushroomBodyColor = new Color(0x00FF00);
        } else if (mushroomBody instanceof Poralia) {
            mushroomBodyColor = new Color(0x0000FF);
        } else if (mushroomBody instanceof Capulon) {
            mushroomBodyColor = new Color(0xFF00FF);
        } else {
            mushroomBodyColor = new Color(0x000000);
        }
        g2d.setColor(mushroomBodyColor);
        g2d.fillOval(position.x + radius / 4, position.y + radius / 4, radius / 5, radius / 5);

        // Draw the insects
        for (InsectView insectView : insectViews) {
            insectView.draw(new Position(position.x + radius / 4, position.y + radius / 4), scale, frame);
        }

        // Draw the mycelium
        for (MyceliumView myceliumView : myceliumViews) {
            myceliumView.draw(new Position(position.x + radius / 4, position.y + radius / 4), scale, frame);
        }
    }

    public void showSpores() {
        // TODO: Implement the logic to show spores
    }
    public void hideSpores() {
        // TODO: Implement the logic to hide spores
    }
}

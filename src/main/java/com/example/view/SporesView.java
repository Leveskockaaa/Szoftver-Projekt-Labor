package com.example.view;

import com.example.model.Spore;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SporesView implements Drawable {
    private List<Spore> sporeList;
    private HashMap<String, Color> colorMap = new HashMap<>();
    private Position position = new Position(1140, 45);

    public SporesView(List<Spore> sporeList) {
        this.sporeList = sporeList;
        colorMap.put("HypharaSpore", new Color(Color.RED.getRGB()));
        colorMap.put("GilledonSpore", new Color(Color.GREEN.getRGB()));
        colorMap.put("PoraliaSpore", new Color(Color.BLUE.getRGB()));
        colorMap.put("CapulonSpore", new Color(Color.PINK.getRGB()));
    }

    public void draw(float scale, Graphics2D g2d) {
        int offsetY = 0; // Adjust the vertical offset for each spore

        g2d.drawRect(1120, 20, 60, 250); // Draw a rectangle for the spores

        for (int i = 0; i < sporeList.size(); i++) {
            String sporeType = sporeList.get(i).getMushroomBody().printType();
            System.out.println(sporeType);
            switch (sporeType) {
                case "Hyphara" -> {
                    g2d.setColor(colorMap.get("HypharaSpore"));
                    offsetY = 0;
                }
                case "Gilledon" -> {
                    g2d.setColor(colorMap.get("GilledonSpore"));
                    offsetY = 50;
                }
                case "Poralia" -> {
                    g2d.setColor(colorMap.get("PoraliaSpore"));
                    offsetY = 100;
                }
                case "Capulon" -> {
                    g2d.setColor(colorMap.get("CapulonSpore"));
                    offsetY = 150;
                }
            }
            int size = 30; // Adjust the size of the triangle as needed
            int[] xPoints = {position.x, position.x + size / 2, position.x - size / 2};
            int[] yPoints = {position.y - size / 2  + offsetY, position.y + size / 2 + offsetY, position.y + size / 2 + offsetY};
            int nPoints = 3;


            // Create a Polygon object
            Polygon triangle = new Polygon(xPoints, yPoints, nPoints);
            g2d.fillPolygon(triangle);

            // Draw the number inside the triangle
            g2d.setColor(Color.WHITE); // Set color for the number
            String number = numberOfSporesType(sporeList.get(i).printType()) + "";
            System.out.println(number);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(number);
            int textHeight = fm.getHeight();
            int x = position.x - textWidth / 2; // Center the text horizontally
            int y = position.y + textHeight / 4 + offsetY; // Center the text vertically
            g2d.drawString(number, x, y);
        }
    }

    private int numberOfSporesType(String spore) {
        int result = 0;
        for (int i = 0; i < sporeList.size(); i++) {
            if (sporeList.get(i).printType().equals(spore)) {
                result++;
            }
        }
        return result;
    }
}

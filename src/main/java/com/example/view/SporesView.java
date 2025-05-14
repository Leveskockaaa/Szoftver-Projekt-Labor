package com.example.view;

import com.example.model.Spore;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class SporesView extends JPanel implements Drawable {
    private List<Spore> sporeList;
    private HashMap<String, Color> colorMap = new HashMap<>();
    private Position position;

    public SporesView(List<Spore> sporeList) {
        this.sporeList = sporeList;
        colorMap.put("HypharaSpore", new Color(Color.RED.getRGB()));
        colorMap.put("GilledonSpore", new Color(Color.GREEN.getRGB()));
        colorMap.put("PoraliaSpore", new Color(Color.BLUE.getRGB()));
        colorMap.put("CapulonSpore", new Color(Color.PINK.getRGB()));
    }


    public void draw(Position position, float scale, JFrame frame) {
        // Implement the drawing logic for the spore here
        // For example, you might use a graphics library to draw the spore shape
        // at the specified position with the given width, height, and rotation.
        ;
        this.position = position;
        frame.add(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



        for (int i = 0; i < sporeList.size(); i++) {
            String sporeType = sporeList.get(i).getMushroomBody().printType();
            System.out.println(sporeType);
            switch (sporeType) {
                case "Hyphara" -> g2d.setColor(colorMap.get("HypharaSpore"));
                case "Gilledon" -> g2d.setColor(colorMap.get("GilledonSpore"));
                case "Poralia" -> g2d.setColor(colorMap.get("PoraliaSpore"));
                case "Capulon" -> g2d.setColor(colorMap.get("CapulonSpore"));
            }
            int size = 30; // Adjust the size of the triangle as needed
            int[] xPoints = {position.x, position.x + size / 2, position.x - size / 2};
            int[] yPoints = {position.y - size / 2, position.y + size / 2, position.y + size / 2};
            int nPoints = 3;


            // Create a Polygon object
            Polygon triangle = new Polygon(xPoints, yPoints, nPoints);
            g2d.fillPolygon(triangle);

            // Draw the number inside the triangle
            g2d.setColor(Color.WHITE); // Set color for the number
            String number = String.valueOf(i + 1); // Get the number as a string
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(number);
            int textHeight = fm.getHeight();
            int x = position.x - textWidth / 2; // Center the text horizontally
            int y = position.y + textHeight / 4; // Center the text vertically
            g2d.drawString(number, x, y);
        }
    }
}

package com.example.view;

import com.example.model.GameTable;
import com.example.model.Mycelium;
import com.example.model.Tecton;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

public class EdgeView extends JPanel {
    GameTable gameTable;
    GameTableView gameTableView;

    public EdgeView(GameTable gameTable, GameTableView gameTableView) {
        this.gameTable = gameTable;
        this.gameTableView = gameTableView;
        setBackground(new Color(0, 0, 0, 0));
        setBounds(0, 0, 1600, 900);
        setPreferredSize(new Dimension(1600, 900));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Map<Tecton, Point> tectonPositions = gameTableView.getTectonPositions();
        // Draw edges
        g2d.setStroke(new BasicStroke(2));
        for (Tecton tecton : gameTable.getTectons()) {
            Point p1 = tectonPositions.get(tecton);
            for (Tecton neighbor : tecton.getNeighbors()) {
                Point p2 = tectonPositions.get(neighbor);
                g2d.setColor(GetLineColor(tecton, neighbor));
                g2d.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
            }
        }
    }

    private Color GetLineColor(Tecton tecton, Tecton neighbor){
        if(tecton.getMycelia().isEmpty()) return Color.BLACK;
        for(Mycelium mycelium : tecton.getMycelia()){
            for(Mycelium neighboringMycelia : mycelium.getMyceliumConnections()){
                if(neighboringMycelia.getTecton() == neighbor){
                    return Color.GREEN;
                }
            }
        }
        return Color.BLACK;
    }
}

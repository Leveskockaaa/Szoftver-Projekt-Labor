package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.example.model.GameTable;
import com.example.model.Insect;
import com.example.model.Mycelium;
import com.example.model.Tecton;

import util.LayeredPane;

public class GameTableView extends LayeredPane {
    private static final int DEFAULT_RADIUS = 15;
    private static final int MAX_ITERATIONS = 5000;
    private static final double REPULSION_FORCE = 1500000;
    private static final double SPRING_CONSTANT = 1;
    private static final int SPRING_REST_LENGTH = 1;
    private static final int MIN_DISTANCE = 10;
    private static final double centerAttractionStrength = 0.1;

    // private final List<TectonView> tectonViews = new ArrayList<>();
    private Map<Tecton, Point> tectonPositions;
    private GameTable gameTable;

    public GameTableView(GameTable gameTable) {
        this.gameTable = gameTable;
        setBackground(new Color(0,0,0,0));
        setBounds(0, 0, 1600, 900);
        validateGameTable();
        setLayout(new BorderLayout());

        // Initialize positions with force-directed layout
        this.tectonPositions = initializePositions(gameTable.getTectons());
        this.tectonPositions = calculateTectonPositions(gameTable);
        System.out.println("Initialized positions for " + tectonPositions.size() + " tectons");
    }

    private void validateGameTable() {
        Objects.requireNonNull(gameTable, "GameTable cannot be null");
        if (gameTable.getTectons() == null) {
            throw new IllegalArgumentException("Tectons list cannot be null");
        }
    }

    public Map<Tecton, Point> getTectonPositions() {
        tectonPositions = calculateTectonPositions(gameTable);
        return tectonPositions;
    }

//    public void updateGameTable(GameTable gameTableNew){
//        tectonPositions = calculateTectonPositions(gameTable);
//        repaint();
//        revalidate();
//    }

    private Map<Tecton, Point> calculateTectonPositions(GameTable gameTable) {
        this.gameTable = gameTable;
        List<Tecton> tectons = gameTable.getTectons();
        Map<Tecton, Point> positions = tectonPositions;

        for(Tecton tecton : gameTable.getTectons()){
            if(!tectonPositions.containsKey(tecton)){
                tectonPositions.put(tecton, new Point(0,0));
            }
        }

        // Adjusted constants
        final double coolingRate = 0.95; // Cooling factor per iteration
        double temperature = 1.0; // Initial temperature

        for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
            Map<Tecton, Point> newPositions = new HashMap<>();
            temperature *= coolingRate; // Cool down each iteration

            for (Tecton tecton : tectons) {
                Point currentPos = positions.get(tecton);
                double dx = 0;
                double dy = 0;

                // 1. Calculate repulsive forces (all nodes repel)
                for (Tecton other : tectons) {
                    if (tecton.equals(other)) continue;

                    Point otherPos = positions.get(other);
                    double dist = Math.max(MIN_DISTANCE, currentPos.distance(otherPos));
                    double repulsion = REPULSION_FORCE / (dist * dist);

                    dx += repulsion * (currentPos.x - otherPos.x) / dist;
                    dy += repulsion * (currentPos.y - otherPos.y) / dist;
                }

                // 2. Calculate spring forces (only between neighbors)
                for (Tecton neighbor : tecton.getNeighbors()) {
                    Point neighborPos = positions.get(neighbor);
                    double dist = Math.max(MIN_DISTANCE, currentPos.distance(neighborPos));
                    double springForce = SPRING_CONSTANT * Math.log(dist / SPRING_REST_LENGTH);

                    dx += springForce * (neighborPos.x - currentPos.x) / dist;
                    dy += springForce * (neighborPos.y - currentPos.y) / dist;
                }

                // 3. Center attraction force
                int centerX = gameTable.getSizeX() / 2;
                int centerY = gameTable.getSizeY() / 2;
                dx += centerAttractionStrength * (centerX - currentPos.x);
                dy += centerAttractionStrength * (centerY - currentPos.y);

                // 4. Apply cooling
                dx *= temperature;
                dy *= temperature;

                // 5. Update position with bounds checking
                int newX = (int) (currentPos.x + dx);
                int newY = (int) (currentPos.y + dy);
                int margin = DEFAULT_RADIUS * 3;
                newX = clamp(newX, margin, gameTable.getSizeX() - margin);
                newY = clamp(newY, margin, gameTable.getSizeY() - margin);

                newPositions.put(tecton, new Point(newX, newY));
            }

            positions = newPositions;
        }

        return positions;
    }

    private Map<Tecton, Point> initializePositions(List<Tecton> tectons) {
        Map<Tecton, Point> positions = new HashMap<>();

        int centerX = gameTable.getSizeX() / 2;
        int centerY = gameTable.getSizeY() / 2;
        int radius = Math.min(centerX, centerY) - DEFAULT_RADIUS * 5;

        double angleStep = 2 * Math.PI / tectons.size();
        double angle = 0;

        for (Tecton tecton : tectons) {
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            positions.put(tecton, new Point(x, y));
            angle += angleStep;
        }
//
//        Tecton tecton = tectons.get(tectons.size() - 1);
//        ArrayList<Tecton> visited = new ArrayList<>();
//        visited.add(tecton);
//        int x = (int) (centerX + radius * Math.cos(angle));
//        int y = (int) (centerY + radius * Math.sin(angle));
//        positions.put(tecton, new Point(x, y));
//        angle += angleStep;
//        loop: while (visited.size() != tectons.size()){
//            for(Tecton neighbor : tecton.getNeighbors()){
//                if(!visited.contains(neighbor)){
//                    x = (int) (centerX + radius * Math.cos(angle));
//                    y = (int) (centerY + radius * Math.sin(angle));
//                    positions.put(neighbor, new Point(x, y));
//                    angle += angleStep;
//                    tecton = neighbor;
//                    visited.add(neighbor);
//                    continue loop;
//                }
//            }
//        }

        return positions;
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }


    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("GameTableView paint called");
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        this.removeAll();

        // Draw nodes

        for (Tecton tect : tectonPositions.keySet()) {
            
            // Draw insects on the tecton
            int insectIndex = 0;
            for (Insect insect : tect.getInsects()) {
                System.out.println("Drawing insect for tecton: " + tect);
               
                Position insectPos = new Position(tectonPositions.get(tect).x - 25 + insectIndex * 30, tectonPositions.get(tect).y + 13);
                insect.getView().setPosition(insectPos);
    
                
                this.add(insect.getView(), BorderLayout.CENTER);
                insect.getView().repaint();
                insect.getView().revalidate();
                insectIndex++;
            }
    
            // Draw mycelia on the tecton
            int myceliumIndex = 0;
            for (Mycelium mycelium : tect.getMycelia()) {
                
                Position myceliumPos = new Position(tectonPositions.get(tect).x + 10 + myceliumIndex * 10, tectonPositions.get(tect).y - 35 + myceliumIndex * 5);
                mycelium.getView().setPosition(myceliumPos);
            
                this.add(mycelium.getView(), BorderLayout.CENTER);
                mycelium.getView().repaint();
                mycelium.getView().revalidate();
                myceliumIndex++;
                
            }
            
            // Draw the mushroom body on the tecton
            if (tect.getMushroomBody() != null) {
                System.out.println("Drawing mushroom body for tecton: " + tect);
                Position mbPos = new Position(tectonPositions.get(tect).x - 35, tectonPositions.get(tect).y - 30);
                tect.getMushroomBody().getView().setPosition(mbPos);
                System.out.println(tect.printType());
                this.add(tect.getMushroomBody().getView(), BorderLayout.CENTER);
                tect.getMushroomBody().getView().repaint();
                tect.getMushroomBody().getView().revalidate();
            }


            // Draw the tecton itself
    
            tect.getView().setPosition(new Position((int)tectonPositions.get(tect).getX() - (tect.getView().getRadius() / 2), (int)tectonPositions.get(tect).getY() - (tect.getView().getRadius() / 2)));
            this.add(tect.getView());
            System.out.println("Position: " + (int)(tectonPositions.get(tect).getX() - ((double) tect.getView().getRadius() / 2.0f)) + " " + (int)(tectonPositions.get(tect).getY() - ((double) tect.getView().getRadius() / 2.0f)));
            tect.getView().repaint();
            tect.getView().revalidate();
        }

        EdgeView edgeView = new EdgeView(gameTable, this);
        this.add(edgeView);

        this.repaint();
        this.revalidate();
    }

//    private Color GetLineColor(Tecton tecton, Tecton neighbor){
//        if(tecton.getMycelia().isEmpty()) return Color.BLACK;
//        for(Mycelium mycelium : tecton.getMycelia()){
//            for(Mycelium neighboringMycelia : mycelium.getMyceliumConnections()){
//                if(neighboringMycelia.getTecton() == neighbor){
//                    return Color.GREEN;
//                }
//            }
//        }
//        return Color.BLACK;
//    }
}

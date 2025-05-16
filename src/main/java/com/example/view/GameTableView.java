package com.example.view;

import com.example.model.*;
import util.LayeredPane;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

public class GameTableView extends LayeredPane {
    private static final int DEFAULT_RADIUS = 15;
    private static final int MAX_ITERATIONS = 5000;
    private static final double REPULSION_FORCE = 1500000;
    private static final double SPRING_CONSTANT = 1;
    private static final int SPRING_REST_LENGTH = 1;
//    private static final double CROSSING_PENALTY_FORCE = 1;
    private static final int MIN_DISTANCE = 10;
    private static final double centerAttractionStrength = 0.1;

//    private final List<TectonView> tectonViews = new ArrayList<>();
    private final Map<Tecton, Point> tectonPositions;
    private final GameTable gameTable;

    public GameTableView(GameTable gameTable) {
        this.gameTable = gameTable;
        validateGameTable();

        // Initialize positions with force-directed layout
        this.tectonPositions = calculateTectonPositions(gameTable);
        //initializeTectonViews();
    }

    private void validateGameTable() {
        Objects.requireNonNull(gameTable, "GameTable cannot be null");
        if (gameTable.getTectons() == null) {
            throw new IllegalArgumentException("Tectons list cannot be null");
        }
    }

//    private void initializeTectonViews() {
//        for (Map.Entry<Tecton, Point> entry : tectonPositions.entrySet()) {
//            Tecton tecton = entry.getKey();
////            Point position = entry.getValue();
//
////            Position pos = new Position();
////            pos.x = position.x;
////            pos.y = position.y;
////            pos.width = DEFAULT_RADIUS * 2;
////            pos.height = DEFAULT_RADIUS * 2;
////            pos.rotation = 0;
//
//            TectonView tectonView = new TectonView(tecton);
//            // tectonView.draw_drawable(pos, 1.0f); // Scale is now handled in TectonView
//            tectonViews.add(tectonView);
//        }
//    }

    private Map<Tecton, Point> calculateTectonPositions(GameTable gameTable) {
        List<Tecton> tectons = gameTable.getTectons();
        Map<Tecton, Point> positions = initializePositions(tectons);

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

                // 3. Calculate crossing penalty forces
//                double[] crossingForces = calculateCrossingForces(tecton, positions);
//                dx += crossingForces[0];
//                dy += crossingForces[1];

                // 4. Center attraction force
                int centerX = gameTable.getSizeX() / 2;
                int centerY = gameTable.getSizeY() / 2;
                dx += centerAttractionStrength * (centerX - currentPos.x);
                dy += centerAttractionStrength * (centerY - currentPos.y);

                // 4. Apply cooling
                dx *= temperature;
                dy *= temperature;

                // 4. Update position with bounds checking
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

//        for (Tecton tecton : tectons) {
//            int x = (int) (centerX + radius * Math.cos(angle));
//            int y = (int) (centerY + radius * Math.sin(angle));
//            positions.put(tecton, new Point(x, y));
//            angle += angleStep;
//        }

        Tecton tecton = tectons.get(tectons.size() - 1);
        ArrayList<Tecton> visited = new ArrayList<>();
        visited.add(tecton);
        int x = (int) (centerX + radius * Math.cos(angle));
        int y = (int) (centerY + radius * Math.sin(angle));
        positions.put(tecton, new Point(x, y));
        angle += angleStep;
        loop: while (visited.size() != tectons.size()){
            for(Tecton neighbor : tecton.getNeighbors()){
                if(!visited.contains(neighbor)){
                    x = (int) (centerX + radius * Math.cos(angle));
                    y = (int) (centerY + radius * Math.sin(angle));
                    positions.put(neighbor, new Point(x, y));
                    angle += angleStep;
                    tecton = neighbor;
                    visited.add(neighbor);
                    continue loop;
                }
            }
        }

        return positions;
    }

//    private double[] calculateCrossingForces(Tecton tecton, Map<Tecton, Point> positions) {
//        double dx = 0;
//        double dy = 0;
//        Point p1 = positions.get(tecton);
//
//        for (Tecton neighbor : tecton.getNeighbors()) {
//            Point p2 = positions.get(neighbor);
//
//            for (Map.Entry<Tecton, Point> entry : positions.entrySet()) {
//                Tecton n = entry.getKey();
//                Point p3 = entry.getValue();
//
//                for (Tecton nb : n.getNeighbors()) {
//                    Point p4 = positions.get(nb);
//
//                    // Skip if same edge or adjacent edges
//                    if (tecton.equals(n) || tecton.equals(nb) ||
//                            neighbor.equals(n) || neighbor.equals(nb)) {
//                        continue;
//                    }
//
//                    if (linesIntersect(p1, p2, p3, p4)) {
//                        double cx = (p1.x + p2.x + p3.x + p4.x) / 4.0;
//                        double cy = (p1.y + p2.y + p3.y + p4.y) / 4.0;
//
//                        dx += CROSSING_PENALTY_FORCE * (cx - p1.x) / 10.0;
//                        dy += CROSSING_PENALTY_FORCE * (cy - p1.y) / 10.0;
//                    }
//                }
//            }
//        }
//
//        return new double[]{dx, dy};
//    }
//
//    private boolean linesIntersect(Point p1, Point p2, Point p3, Point p4) {
//        double x1 = p1.x, y1 = p1.y;
//        double x2 = p2.x, y2 = p2.y;
//        double x3 = p3.x, y3 = p3.y;
//        double x4 = p4.x, y4 = p4.y;
//
//        double d = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
//        if (d == 0) return false;
//
//        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / d;
//        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / d;
//
//        return ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1;
//    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

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

        // Draw nodes
        for (Tecton tect : tectonPositions.keySet()) {
              //Position pos = new Position();
//            pos.x = position.x;
//            pos.y = position.y;
//            pos.width = DEFAULT_RADIUS * 2;
//            pos.height = DEFAULT_RADIUS * 2;
//            pos.rotation = 0;
            tect.getView().setPosition(new Position((int)tectonPositions.get(tect).getX(), (int)tectonPositions.get(tect).getY()));
            this.add(tect.getView());
            tect.getView().repaint();
            tect.getView().revalidate();
        }
        this.repaint();
        this.revalidate();
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

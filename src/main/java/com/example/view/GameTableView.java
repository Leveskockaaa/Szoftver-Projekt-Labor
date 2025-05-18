package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.example.Timer;
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
//    private static final double CROSSING_PENALTY_FORCE = 1;
    private static final int MIN_DISTANCE = 10;
    private static final double centerAttractionStrength = 0.1;
    private transient final Object lock = new Object();

private final List<TectonView> tectonViews = new ArrayList<>();
    private final Map<Tecton, Point> tectonPositions;
    private final GameTable gameTable;
    private TectonView selectedTecton = null;
    private GameCountdownTimer countdownTimer; // Add countdown timer field
    private ScoreWindow scoreWindow; // Pontszámokat megjelenítő ablak

    public GameTableView(GameTable gameTable) {
        this.gameTable = gameTable;
        setBackground(new Color(0,0,0,0));
        setBounds(0, 0, 1600, 900);
        validateGameTable();
        setLayout(new BorderLayout());

        // Initialize positions with force-directed layout
        this.tectonPositions = calculateTectonPositions(gameTable);
        //initializeTectonViews();

        // Initialize and add the score panel to the right side
        initializeScorePanel();

        // Initialize and add the countdown timer (5 minutes)
        initializeCountdownTimer();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedTecton = chooseTecton(e.getX(), e.getY());
                if (selectedTecton != null) {
                    SporesView sporesView = new SporesView(selectedTecton.getTecton().getSpores());
                    add(sporesView, BorderLayout.CENTER);
                    Timer timer = new Timer(5, () -> {
                        selectedTecton = null;
                        remove(sporesView);
                        repaint();
                    });
                }
                repaint();
            }
        });
    }

    public Object getLock() {
        return lock;
    }

    private TectonView chooseTecton(int x, int y) {
        for (Tecton tecton : gameTable.getTectons()) {
            TectonView tectonView = tecton.getView();
                if (tectonView.isSelected(x, y)) {
                    selectedTecton = tectonView;
                    return selectedTecton;
                }
        }
        return null;
    }

    private void validateGameTable() {
        Objects.requireNonNull(gameTable, "GameTable cannot be null");
        if (gameTable.getTectons() == null) {
            throw new IllegalArgumentException("Tectons list cannot be null");
        }
    }

    private void initializeTectonViews() {
        for (Map.Entry<Tecton, Point> entry : tectonPositions.entrySet()) {
            Tecton tecton = entry.getKey();
//            Point position = entry.getValue();

//            Position pos = new Position();
//            pos.x = position.x;
//            pos.y = position.y;
//            pos.width = DEFAULT_RADIUS * 2;
//            pos.height = DEFAULT_RADIUS * 2;
//            pos.rotation = 0;

            TectonView tectonView = new TectonView(tecton);
            // tectonView.draw_drawable(pos, 1.0f); // Scale is now handled in TectonView
            tectonViews.add(tectonView);
        }
    }

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

        if (selectedTecton != null) {
            SporesView sporesView = new SporesView(selectedTecton.getTecton().getSpores());
            add(sporesView);
            Timer timer = new Timer(5, () -> {
                selectedTecton = null;
                repaint();
            });
        }

        // Draw nodes

        for (Tecton tect : tectonPositions.keySet()) {

            // Draw insects on the tecton
            int insectIndex = 0;
            for (Insect insect : tect.getInsects()) {

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

                this.add(tect.getMushroomBody().getView(), BorderLayout.CENTER);
                tect.getMushroomBody().getView().repaint();
                tect.getMushroomBody().getView().revalidate();
            }


            // Draw the tecton itself

            tect.getView().setPosition(new Position((int)tectonPositions.get(tect).getX() - (tect.getView().getRadius() / 2), (int)tectonPositions.get(tect).getY() - (tect.getView().getRadius() / 2)));
            this.add(tect.getView());
            System.out.println("Position: " + (int)(tectonPositions.get(tect).getX() - (tect.getView().getRadius() / 2)) + " " + (int)(tectonPositions.get(tect).getY() - (tect.getView().getRadius() / 2)));
            tect.getView().repaint();
            tect.getView().revalidate();
        }

        EdgeView edgeView = new EdgeView(gameTable, tectonPositions);
        this.add(edgeView);
        // Az időzítő címke már a ScoreWindow-ban van, így nem adjuk hozzá a játéktérhez
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

    /**
     * Initializes the game countdown timer and adds it to the ScoreWindow.
     */
    private void initializeCountdownTimer() {
        // Create a 5-minute countdown timer
        countdownTimer = new GameCountdownTimer(5);

        // Beállítjuk a timer címkét
        JLabel timerLabel = countdownTimer.getTimerLabel();
        timerLabel.setForeground(Color.BLACK);

        // A timert átadjuk a különálló pontszám ablaknak
        if (scoreWindow != null) {
            scoreWindow.setTimerLabel(timerLabel);
        }

        // Elindítjuk a visszaszámlálót
        countdownTimer.start();
    }

    /**
     * Inicializálja a pontokat megjelenítő panelt egy külön ablakban
     */
    private void initializeScorePanel() {
        // Létrehozzuk a pontszám ablakot
        scoreWindow = new ScoreWindow();
    }

    /**
     * Frissíti a játékosok pontszámait
     *
     * @param mycologist1Score Mycologist1 pontszáma
     * @param mycologist2Score Mycologist2 pontszáma
     * @param entomologist1Score Entomologist1 pontszáma
     * @param entomologist2Score Entomologist2 pontszáma
     */
    public void updateScores(int mycologist1Score, int mycologist2Score, int entomologist1Score, int entomologist2Score) {
        if (scoreWindow != null) {
            scoreWindow.updateScores(mycologist1Score, mycologist2Score, entomologist1Score, entomologist2Score);
        }
    }
}

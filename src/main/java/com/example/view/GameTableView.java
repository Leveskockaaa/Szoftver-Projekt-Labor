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
    private static final int MIN_DISTANCE = 10;
    private static final double centerAttractionStrength = 0.1;
    private transient final Object lock = new Object();

    private final List<TectonView> tectonViews = new ArrayList<>();
    private final Map<Tecton, Point> tectonPositions;
    private final GameTable gameTable;
    private TectonView selectedTecton = null;

    public GameTableView(GameTable gameTable) {
        this.gameTable = gameTable;
        setBackground(new Color(0, 0, 0, 0));
        setBounds(0, 0, 1600, 900);
        validateGameTable();
        setLayout(new BorderLayout());

        // Initialize positions with force-directed layout
        this.tectonPositions = calculateTectonPositions(gameTable);

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
            TectonView tectonView = new TectonView(tecton);
            tectonViews.add(tectonView);
        }
    }

    private Map<Tecton, Point> calculateTectonPositions(GameTable gameTable) {
        List<Tecton> tectons = gameTable.getTectons();
        Map<Tecton, Point> positions = initializePositions(tectons);

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

        Tecton tecton = tectons.get(tectons.size() - 1);
        ArrayList<Tecton> visited = new ArrayList<>();
        visited.add(tecton);
        int x = (int) (centerX + radius * Math.cos(angle));
        int y = (int) (centerY + radius * Math.sin(angle));
        positions.put(tecton, new Point(x, y));
        angle += angleStep;
        loop: while (visited.size() != tectons.size()) {
            for (Tecton neighbor : tecton.getNeighbors()) {
                if (!visited.contains(neighbor)) {
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

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private void addAllViewsOnce() {
        // 1. Add mycelia first (lowest z-order)
        for (Tecton tecton : gameTable.getTectons()) {
            int myceliumIndex = 0;
            for (Mycelium mycelium : tecton.getMycelia()) {
                MyceliumView mycView = mycelium.getView();
                mycView.setPosition(new Position(
                    tectonPositions.get(tecton).x + 10 + myceliumIndex * 10,
                    tectonPositions.get(tecton).y - 35 + myceliumIndex * 5
                ));
                this.add(mycView);
                myceliumIndex++;
            }
        }
        // 2. Add insects
        for (Tecton tecton : gameTable.getTectons()) {
            int insectIndex = 0;
            for (Insect insect : tecton.getInsects()) {
                InsectView insView = insect.getView();
                insView.setPosition(new Position(
                    tectonPositions.get(tecton).x - 25 + insectIndex * 30,
                    tectonPositions.get(tecton).y + 13
                ));
                this.add(insView);
                insectIndex++;
            }
        }
        // 3. Add mushroom bodies
        for (Tecton tecton : gameTable.getTectons()) {
            if (tecton.getMushroomBody() != null) {
                MushroomBodyView mbView = tecton.getMushroomBody().getView();
                mbView.setPosition(new Position(
                    tectonPositions.get(tecton).x - 35,
                    tectonPositions.get(tecton).y - 30
                ));
                this.add(mbView);
            }
        }
        // 4. Add tectons last (highest z-order)
        for (Tecton tecton : gameTable.getTectons()) {
            tecton.getView().setPosition(new Position(
                tectonPositions.get(tecton).x - (tecton.getView().getRadius() / 2),
                tectonPositions.get(tecton).y - (tecton.getView().getRadius() / 2)
            ));
            this.add(tecton.getView());
        }
    }

    // Helper method to add a new Mycelium to the view after initialization
    public void addNewMycelium(Mycelium newMycelium) {
        // Add mycelium at the lowest z-order (index 0)
        int idx = newMycelium.getTecton().getMycelia().indexOf(newMycelium);
        Position pos = new Position(
            tectonPositions.get(newMycelium.getTecton()).x + 10 + idx * 10,
            tectonPositions.get(newMycelium.getTecton()).y - 35 + idx * 5
        );
        newMycelium.getView().setPosition(pos);
        this.add(newMycelium.getView(), 0); // add at bottom
        this.revalidate();
        this.repaint();
    }


    public void updateInsect(Insect newInsect) {
        int idx = newInsect.getTecton().getInsects().indexOf(newInsect);
        Position pos = new Position(
                tectonPositions.get(newInsect.getTecton()).x - 25 + idx * 30,
                tectonPositions.get(newInsect.getTecton()).y + 13
        );
        newInsect.getView().setPosition(pos);
        this.add(newInsect.getView(), 0); // add at bottom
        this.revalidate();
        this.repaint();
    }

    public void addNewMushroomBody(MushroomBody mb){
        int idx = mb.getTecton().getMycelia().indexOf(mb);
        Position pos = new Position(
                tectonPositions.get(mb.getTecton()).x - 35,
                tectonPositions.get(mb.getTecton()).y - 30
        );
        mb.getView().setPosition(pos);
        this.add(mb.getView(), 0); // add at bottom
        this.revalidate();
        this.repaint();
    }

    public void removeMycelium(Mycelium my){
        this.remove(my.getView());
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

//        if (selectedTecton != null) {
//            SporesView sporesView = new SporesView(selectedTecton.getTecton().getSpores());
//            add(sporesView);
//            Timer timer = new Timer(5, () -> {
//                selectedTecton = null;
//                repaint();
//            });
//        }

        addAllViewsOnce();

        EdgeView edgeView = new EdgeView(gameTable, tectonPositions);
        this.add(edgeView);
        repaint();
    }

}

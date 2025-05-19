package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JLabel;

import com.example.Timer;
import com.example.model.GameTable;
import com.example.model.Insect;
import com.example.model.MushroomBody;
import com.example.model.Mycelium;
import com.example.model.Tecton;
import com.example.model.TectonSize;

import util.LayeredPane;

public class GameTableView extends LayeredPane {
    private static final int DEFAULT_RADIUS = 15;
    private static final int MAX_ITERATIONS = 1000;
    private static final double REPULSION_FORCE = 1500000;
    private static final double SPRING_CONSTANT = 1;
    private static final int SPRING_REST_LENGTH = 1;
    private static final double CROSSING_PENALTY_FORCE = 0.2;
    private static final int MIN_DISTANCE = 5;
    private static final double centerAttractionStrength = 0.1;
    private static transient final Object lock = new Object();

    private Map<Tecton, Point> tectonPositions;
    private Map<Tecton, TectonView> tectonViews;
    private GameTable gameTable;
    private TectonView selectedTecton = null;
    private EdgeView edgeView;
    private GameCountdownTimer countdownTimer; // Add countdown timer field
    private ScoreWindow scoreWindow; // Pontszámokat megjelenítő ablak

    public GameTableView(GameTable gameTable) {
        this.tectonViews = new HashMap<>();
        this.gameTable = gameTable;
        setBackground(new Color(0, 0, 0, 0));
        setBounds(0, 0, 1600, 850);
        validateGameTable();
        setLayout(new BorderLayout());

        // Initialize positions with force-directed layout
        this.tectonPositions = calculateTectonPositions(gameTable);
        addAllViewsOnce();

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

    public static void notifyLock() {
        synchronized (lock) {
            lock.notify();
        }
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

                // 3. Crossing penalty
                double[] crossingForces = calculateCrossingForces(tecton, positions);
                dx += crossingForces[0];
                dy += crossingForces[1];

                // 4. Center attraction force
                int centerX = gameTable.getSizeX() / 2;
                int centerY = gameTable.getSizeY() / 2;
                dx += centerAttractionStrength * (centerX - currentPos.x);
                dy += centerAttractionStrength * (centerY - currentPos.y);

                // 5. Apply cooling
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

    private double[] calculateCrossingForces(Tecton tecton, Map<Tecton, Point> positions) {
        double dx = 0;
        double dy = 0;
        Point p1 = positions.get(tecton);

        for (Tecton neighbor : tecton.getNeighbors()) {
            Point p2 = positions.get(neighbor);

            for (Map.Entry<Tecton, Point> entry : positions.entrySet()) {
                Tecton n = entry.getKey();
                Point p3 = entry.getValue();

                for (Tecton nb : n.getNeighbors()) {
                    Point p4 = positions.get(nb);

                    // Skip if same edge or adjacent edges
                    if (tecton.equals(n) || tecton.equals(nb) ||
                            neighbor.equals(n) || neighbor.equals(nb)) {
                        continue;
                    }

                    if (linesIntersect(p1, p2, p3, p4)) {
                        double cx = (p1.x + p2.x + p3.x + p4.x) / 4.0;
                        double cy = (p1.y + p2.y + p3.y + p4.y) / 4.0;

                        dx += CROSSING_PENALTY_FORCE * (cx - p1.x) / 10.0;
                        dy += CROSSING_PENALTY_FORCE * (cy - p1.y) / 10.0;
                    }
                }
            }
        }

        return new double[]{dx, dy};
    }

    private boolean linesIntersect(Point p1, Point p2, Point p3, Point p4) {
        double x1 = p1.x, y1 = p1.y;
        double x2 = p2.x, y2 = p2.y;
        double x3 = p3.x, y3 = p3.y;
        double x4 = p4.x, y4 = p4.y;

        double d = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        if (d == 0) return false;

        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / d;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / d;

        return ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1;
    }

    private Map<Tecton, Point> initializePositions(List<Tecton> tectons) {
        Map<Tecton, Point> positions = new HashMap<>();

        int centerX = gameTable.getSizeX() / 2;
        int centerY = gameTable.getSizeY() / 2;
        int radius = Math.min(centerX, centerY) - DEFAULT_RADIUS * 5;

        double angleStep = 2 * Math.PI / tectons.size();
        double angle = 0;

        for(Tecton tecton : tectons) {
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            positions.put(tecton, new Point(x, y));
            angle += angleStep;
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
                mycView.setScale(TectonSizeToScale(tecton));
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
                insView.setScale(TectonSizeToScale(tecton));
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
                mbView.setScale(TectonSizeToScale(tecton));
                this.add(mbView);
            }
        }
        // 4. Add tectons
        for (Tecton tecton : gameTable.getTectons()) {
            tecton.getView().setPosition(new Position(
                tectonPositions.get(tecton).x - (tecton.getView().getRadius() / 2),
                tectonPositions.get(tecton).y - (tecton.getView().getRadius() / 2)
            ));
            tectonViews.put(tecton, tecton.getView());
            this.add(tecton.getView());
        }
        // 5. Add edges last (highest z-order)
        edgeView = new EdgeView(gameTable, tectonPositions);
        this.add(edgeView);
    }

    public static float TectonSizeToScale(Tecton tecton){
        TectonSize tectonSize = tecton.getSize();
        switch(tectonSize){
            case SMALL -> {
                return 0.25f;
            }
            case MEDIUM -> {
                return 0.50f;
            }
            case BIG -> {
                return 0.75f;
            }
            case GIANT -> {
                return 1.25f;
            }
        }
        return 1;
    }

    public void addNewMushroomBody(MushroomBody mb){
        Position pos = new Position(
                tectonPositions.get(mb.getTecton()).x - 35,
                tectonPositions.get(mb.getTecton()).y - 30
        );
        mb.getView().setPosition(pos);
        this.add(mb.getView(), 2);
        this.revalidate();
        this.repaint();
    }

    // Add functions for the views
    public void addNewMycelium(Mycelium newMycelium) {
        int idx = newMycelium.getTecton().getMycelia().indexOf(newMycelium);
        Position pos = new Position(
            tectonPositions.get(newMycelium.getTecton()).x + 10 + idx * 10,
            tectonPositions.get(newMycelium.getTecton()).y - 35 + idx * 5
        );
        newMycelium.getView().setPosition(pos);
        this.add(newMycelium.getView(), 0);
        this.revalidate();
        this.repaint();
    }

    public void addInsect(Insect newInsect) {
        int idx = newInsect.getTecton().getInsects().indexOf(newInsect);
        Position pos = new Position(
                tectonPositions.get(newInsect.getTecton()).x - 25 + idx * 30,
                tectonPositions.get(newInsect.getTecton()).y + 13
        );
        newInsect.getView().setPosition(pos);
        this.add(newInsect.getView(), 1);
        this.revalidate();
        this.repaint();
    }

    // Remove functions for the views
    public void removeInsect(Insect insect) {
        this.remove(insect.getView());
        this.revalidate();
        this.repaint();
    }

    public void removeMycelium(Mycelium my){
        this.remove(my.getView());
        this.revalidate();
        this.repaint();
    }

    // Update functions for the views
    public void updateInsect(Insect insect) {
        int idx = insect.getTecton().getInsects().indexOf(insect);
        Position pos = new Position(
                tectonPositions.get(insect.getTecton()).x - 25 + idx * 30,
                tectonPositions.get(insect.getTecton()).y + 13
        );
        insect.getView().setPosition(pos);
    }

    public void updateMycelium(Mycelium mycelium){
        int idx = mycelium.getTecton().getMycelia().indexOf(mycelium);
        Position pos = new Position(
                tectonPositions.get(mycelium.getTecton()).x + 10 + idx * 10,
                tectonPositions.get(mycelium.getTecton()).y - 35 + idx * 5
        );
        mycelium.getView().setPosition(pos);
    }

    public void updateMushroomBody(MushroomBody mushroomBody){
        Position pos = new Position(
                tectonPositions.get(mushroomBody.getTecton()).x - 35,
                tectonPositions.get(mushroomBody.getTecton()).y - 30
        );
        mushroomBody.getView().setPosition(pos);
    }

    public Map<Tecton, Point> updateGameTable(){
        tectonPositions = calculateTectonPositions(gameTable);
        for (Tecton tecton : gameTable.getTectons()) {
            // Update tecton position
            Point newPoint = tectonPositions.get(tecton);
            tecton.getView().setPosition(new Position(newPoint.x - tecton.getView().getRadius() / 2, newPoint.y - tecton.getView().getRadius() / 2));

            // Update tecton components' positions
            if (tecton.hasMushroomBody()) {
                updateMushroomBody(tecton.getMushroomBody());
            }
            for (Insect insect : tecton.getInsects()) {
                updateInsect(insect);
            }
            for (Mycelium mycelium : tecton.getMycelia()) {
                updateMycelium(mycelium);
            }

            // Add any new tecton to the positions map and the pane
            if (!tectonViews.containsKey(tecton)) {
                tectonViews.put(tecton, tecton.getView());
                this.add(tecton.getView());
                this.remove(edgeView);
                edgeView = new EdgeView(gameTable, tectonPositions);
                this.add(edgeView, -1);
            }
        }
        // Remove any disappearing tecton from the views and the pane
        Iterator<Tecton> iterator = tectonViews.keySet().iterator();
        while (iterator.hasNext()) {
            Tecton tecton = iterator.next();
            if (!gameTable.getTectons().contains(tecton)) {
                iterator.remove();
                this.remove(tecton.getView());
            }
        }
        this.remove(edgeView);
        edgeView = new EdgeView(gameTable, tectonPositions);
        this.add(edgeView, -1);
        repaint();
        return tectonPositions;
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

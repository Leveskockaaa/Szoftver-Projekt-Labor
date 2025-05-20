package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 * Combined panel for displaying game statistics (scores and countdown timer).
 * This panel is designed to be added to a JLayeredPane.
 */
public class InfoPanel extends JPanel {
    private static JLabel mycologist1ScoreLabel;
    private static JLabel mycologist2ScoreLabel;
    private static JLabel entomologist1ScoreLabel;
    private static JLabel entomologist2ScoreLabel;
    private JLabel countdownLabel;
    private int remainingSeconds = 300;
    private Timer timer;
    private final GameTableView gameTableView;

    public InfoPanel(GameTableView gameTableView) {
        this.gameTableView = gameTableView;
        
        // Panel setup
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(0, 20, 0, 0));
        setPreferredSize(new Dimension(300, 400));

        // Create score components
        createScorePanel();
        
        // Add spacing
        add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Create countdown components
        createCountdownPanel();
    }

    private void createScorePanel() {
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(new Color(0, 0, 0, 0));
        scorePanel.setOpaque(false);
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Font scoreFont = new Font("JetBrains Mono", Font.BOLD, 16);

        JLabel titleLabel = new JLabel("PONTSZÁMOK");
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Initialize score labels
        mycologist1ScoreLabel = new JLabel("Mycologist1: 0 pont");
        mycologist1ScoreLabel.setFont(scoreFont);
        mycologist1ScoreLabel.setForeground(Color.BLACK);
        mycologist1ScoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mycologist2ScoreLabel = new JLabel("Mycologist2: 0 pont");
        mycologist2ScoreLabel.setFont(scoreFont);
        mycologist2ScoreLabel.setForeground(Color.BLACK);
        mycologist2ScoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        entomologist1ScoreLabel = new JLabel("Entomologist1: 0 pont");
        entomologist1ScoreLabel.setFont(scoreFont);
        entomologist1ScoreLabel.setForeground(Color.BLACK);
        entomologist1ScoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        entomologist2ScoreLabel = new JLabel("Entomologist2: 0 pont");
        entomologist2ScoreLabel.setFont(scoreFont);
        entomologist2ScoreLabel.setForeground(Color.BLACK);
        entomologist2ScoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add components to score panel
        scorePanel.add(titleLabel);
        scorePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        scorePanel.add(mycologist1ScoreLabel);
        scorePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        scorePanel.add(mycologist2ScoreLabel);
        scorePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        scorePanel.add(entomologist1ScoreLabel);
        scorePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        scorePanel.add(entomologist2ScoreLabel);
        
        // Add score panel to main panel
        add(scorePanel);
    }

    private void createCountdownPanel() {
        JPanel countdownPanel = new JPanel(new BorderLayout());
        countdownPanel.setBackground(new Color(0, 0, 0, 0));
        countdownPanel.setOpaque(false);
        countdownPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        countdownLabel = new JLabel(formatTime(remainingSeconds), SwingConstants.LEFT);
        countdownLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        countdownLabel.setForeground(new Color(220, 20, 20));
        
        countdownPanel.add(countdownLabel, BorderLayout.NORTH);
        
        // Timer setup
        timer = new Timer(1000, event -> {
            if (remainingSeconds > 0) {
                remainingSeconds--;
                countdownLabel.setText(formatTime(remainingSeconds));
                gameTableView.revalidate();
                gameTableView.repaint();
            } else {
                stopTimer();
            }
        });
        timer.start();
        
        add(countdownPanel);
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
            synchronized (gameTableView.getLock()) {
                gameTableView.getLock().notifyAll();
            }
        }
    }

    // Score update methods
    public static void updateMycologist1Score(int score) {
        mycologist1ScoreLabel.setText("Mycologist1: " + score + " pont");
    }

    public static void updateMycologist2Score(int score) {
        mycologist2ScoreLabel.setText("Mycologist2: " + score + " pont");
    }

    public static void updateEntomologist1Score(int score) {
        entomologist1ScoreLabel.setText("Entomologist1: " + score + " pont");
    }

    public static void updateEntomologist2Score(int score) {
        entomologist2ScoreLabel.setText("Entomologist2: " + score + " pont");
    }

    /**
     * Updates all scores at once
     */
    public void updateScores(int mycologist1Score, int mycologist2Score, int entomologist1Score, int entomologist2Score) {
        updateMycologist1Score(mycologist1Score);
        updateMycologist2Score(mycologist2Score);
        updateEntomologist1Score(entomologist1Score);
        updateEntomologist2Score(entomologist2Score);
    }
}
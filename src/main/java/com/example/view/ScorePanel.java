package com.example.view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel a játékosok pontszámainak megjelenítésére.
 * A jobboldali részen jeleníti meg a pontokat egymás alatt.
 */
public class ScorePanel extends JPanel {
    private JLabel mycologist1ScoreLabel;
    private JLabel mycologist2ScoreLabel;
    private JLabel entomologist1ScoreLabel;
    private JLabel entomologist2ScoreLabel;

    /**
     * Létrehoz egy új ScorePanel-t a játékosok pontszámainak megjelenítésére.
     */
    public ScorePanel() {
        setBackground( new Color( 0, 0, 0, 0 ) );
        setBounds( 0, 0, 1600, 900 );

        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Létrehozzuk a címkéket a pontszámoknak
        Font scoreFont = new Font("JetBrains Mono", Font.BOLD, 16);

        JLabel titleLabel = new JLabel("PONTSZÁMOK");
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        // Hozzáadjuk a címkéket a panelhez
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20))); // Térköz
        add(mycologist1ScoreLabel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Térköz
        add(mycologist2ScoreLabel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Térköz
        add(entomologist1ScoreLabel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Térköz
        add(entomologist2ScoreLabel);
    }

    /**
     * Frissíti a Mycologist1 pontszámát.
     *
     * @param score Az új pontszám
     */
    public void updateMycologist1Score(int score) {
        mycologist1ScoreLabel.setText("Mycologist1: " + score + " pont");
    }

    /**
     * Frissíti a Mycologist2 pontszámát.
     *
     * @param score Az új pontszám
     */
    public void updateMycologist2Score(int score) {
        mycologist2ScoreLabel.setText("Mycologist2: " + score + " pont");
    }

    /**
     * Frissíti az Entomologist1 pontszámát.
     *
     * @param score Az új pontszám
     */
    public void updateEntomologist1Score(int score) {
        entomologist1ScoreLabel.setText("Entomologist1: " + score + " pont");
    }

    /**
     * Frissíti az Entomologist2 pontszámát.
     *
     * @param score Az új pontszám
     */
    public void updateEntomologist2Score(int score) {
        entomologist2ScoreLabel.setText("Entomologist2: " + score + " pont");
    }
}

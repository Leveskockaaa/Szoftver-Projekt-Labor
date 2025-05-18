package com.example.view;

import javax.swing.*;
import java.awt.*;

/**
 * Külön ablak a játékosok pontszámainak és a visszaszámláló időzítőnek a megjelenítésére.
 */
public class ScoreWindow extends JFrame {
    private ScorePanel scorePanel;
    private JLabel timerLabel;

    /**
     * Létrehoz egy új ablakot a pontszámok és az időzítő megjelenítésére.
     */
    public ScoreWindow() {
        // Ablak beállítások
        setTitle("Játék Információk");
        setSize(300, 300); // Magasabb ablak az időzítő miatt
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Nem engedjük bezárni
        setResizable(false);
        setUndecorated(true); // Eltüntetjük az ablak keretét

        // Képernyő közepéhez képest jobbra helyezzük el
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width - getWidth() - 20, 50);

        // Panel a tartalom rendezéséhez
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Vékony szegély a láthatóság érdekében

        // Létrehozzuk és hozzáadjuk a pontszám panelt
        scorePanel = new ScorePanel();
        contentPanel.add(scorePanel, BorderLayout.CENTER);

        // Létrehozzuk az időzítő címkét
        timerLabel = new JLabel("Teljes idő: 05:00");
        timerLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 16));
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Az időzítőt a panel aljára helyezzük
        contentPanel.add(timerLabel, BorderLayout.SOUTH);

        // Beállítjuk a content panel-t az ablak tartalmának
        getContentPane().add(contentPanel);

        // Megjelenítjük az ablakot
        setVisible(true);
    }

    /**
     * Frissíti a játékosok pontszámait.
     *
     * @param mycologist1Score Mycologist1 pontszáma
     * @param mycologist2Score Mycologist2 pontszáma
     * @param entomologist1Score Entomologist1 pontszáma
     * @param entomologist2Score Entomologist2 pontszáma
     */
    public void updateScores(int mycologist1Score, int mycologist2Score, int entomologist1Score, int entomologist2Score) {
        if (scorePanel != null) {
            scorePanel.updateMycologist1Score(mycologist1Score);
            scorePanel.updateMycologist2Score(mycologist2Score);
            scorePanel.updateEntomologist1Score(entomologist1Score);
            scorePanel.updateEntomologist2Score(entomologist2Score);
        }
    }

    /**
     * Beállítja a visszaszámláló időzítő címkét.
     *
     * @param timerLabel Az időzítő címke
     */
    public void setTimerLabel(JLabel timerLabel) {
        if (timerLabel != null) {
            // Eltávolítjuk a régi címkét, ha van
            Container contentPane = getContentPane();
            if (contentPane instanceof JPanel) {
                JPanel contentPanel = (JPanel) contentPane;
                Component oldTimerLabel = ((BorderLayout)contentPanel.getLayout()).getLayoutComponent(BorderLayout.SOUTH);
                if (oldTimerLabel != null) {
                    contentPanel.remove(oldTimerLabel);
                }
            }

            // Beállítjuk az új címkét
            this.timerLabel = timerLabel;
            timerLabel.setHorizontalAlignment(JLabel.CENTER);
            timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

            if (contentPane instanceof JPanel) {
                JPanel contentPanel = (JPanel) contentPane;
                contentPanel.add(timerLabel, BorderLayout.SOUTH);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        }
    }
}

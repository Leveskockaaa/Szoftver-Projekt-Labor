package util;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLayeredPane;

public class LayeredPane extends JLayeredPane {
    public LayeredPane() {
        super();
        setBackground(new Color(0,0,0,0));
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        setBounds(0,0,getWidth(), getHeight());
    }
}
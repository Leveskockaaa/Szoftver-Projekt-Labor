package util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class StyledButton extends BasicButtonUI {
    @Override
    public void installUI (JComponent component) {
        super.installUI(component);
        AbstractButton button = (AbstractButton) component;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint (Graphics graphics, JComponent component) {
        AbstractButton button = (AbstractButton) component;
        paintBackground(graphics, button, button.getModel().isPressed() ? 2 : 0);
        super.paint(graphics, component);
    }

    private void paintBackground (Graphics graphics, JComponent component, int yOffset) {
        Dimension size = component.getSize();
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(component.getBackground().darker());
        graphics.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        graphics.setColor(component.getBackground());
        graphics.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }
}


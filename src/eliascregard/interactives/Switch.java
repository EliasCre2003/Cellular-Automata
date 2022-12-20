package eliascregard.interactives;

import eliascregard.input.MouseHandler;
import eliascregard.physics.Vector2D;

import java.awt.*;

public class Switch {
    private Color outlineColor;
    private Color insideColor;
    private String label;
    public boolean isOn;
    private Vector2D position;
    private int width;
    private int height;

    public Switch(Color outlineColor, Color insideColor, String label, Vector2D position, int width, int height) {
        this.outlineColor = outlineColor;
        this.insideColor = insideColor;
        this.label = label;
        this.isOn = false;
        this.position = position;
        this.width = width;
        this.height = height;

    }

    public void update(MouseHandler mouse) {
        if (mouse.leftIsPressed()) {
            if (mouse.getX() > this.position.x && mouse.getX() < this.position.x + this.width &&
                mouse.getY() > this.position.y && mouse.getY() < this.position.y + this.height) {
                mouse.setLeftIsPressed(false);
                this.isOn = !this.isOn;
            }
        }
    }

    public Color getOutlineColor() {
        return this.outlineColor;
    }

    public Color getInsideColor() {
        return this.insideColor;
    }

    public String getLabel() {
        return this.label;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public void render(Graphics2D g2, double scale) {
        if (this.isOn) {
            this.outlineColor = Color.RED;
        }
        else {
            this.outlineColor = Color.WHITE;
        }
        g2.setColor(this.outlineColor);
        g2.fillRect((int) (this.position.x * scale), (int) (this.position.y * scale), (int) (this.width * scale), (int) (this.height * scale));
        g2.setColor(this.insideColor);
        g2.fillRect((int) ((position.x + 5) * scale), (int) ((position.y + 5) * scale), (int) ((this.width - 10) * scale), (int) ((this.height - 10) * scale));
        g2.setColor(new Color(255, 255, 255));
        g2.drawString(this.label, (int) ((this.position.x + 25) * scale), (int) ((this.position.y + 15) * scale));
    }

}

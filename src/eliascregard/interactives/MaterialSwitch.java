package eliascregard.interactives;

import eliascregard.cells.Cell;
import eliascregard.physics.Vector2D;

import java.awt.*;

public class MaterialSwitch extends Switch {
    private Class<? extends Cell> material;

    public MaterialSwitch(
            Class<? extends Cell> material,
            Color outlineColor, Color insideColor, String label, Vector2D position, int width, int height) {
        super(outlineColor, insideColor, label, position, width, height);
        this.material = material;
    }

    public Class<? extends Cell> getMaterial() {
        return this.material;
    }

    public void setMaterial(Class<? extends Cell> material) {
        this.material = material;
    }

}

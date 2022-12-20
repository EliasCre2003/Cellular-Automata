package eliascregard.interactives;

import eliascregard.input.MouseHandler;

import java.awt.*;
import java.util.Arrays;

public class RadioButtons {

    public Switch[] switches;
    public Switch activeSwitch = null;

    public RadioButtons(Switch[] switches) {
        this.switches = switches;
    }
    public RadioButtons() {
        switches = new Switch[0];
    }

    public void update(MouseHandler mouse) {
        if (!mouse.leftIsPressed()) {
            return;
        }
        for (Switch s : this.switches) {
            boolean previousState = s.isOn;
            s.update(mouse);
            if (s.isOn != previousState) {
                if (s.isOn) {
                    this.activeSwitch.isOn = false;
                    this.activeSwitch = s;
                } else {
                    s.isOn = true;
                }
                return;
            }
        }
    }

    public void addButtons(Switch s) {
        this.switches = Arrays.copyOf(this.switches, this.switches.length + 1);
        this.switches[this.switches.length - 1] = s;
    }

    public void addButtons(Switch[] switches) {
        this.switches = Arrays.copyOf(this.switches, this.switches.length + switches.length);
        System.arraycopy(switches, 0, this.switches, this.switches.length - switches.length, switches.length);
    }

    public void render(Graphics2D g2, double scale) {
        for (Switch s : this.switches) {
            s.render(g2, scale);
        }
    }

    public Switch getSelectedButton() {
        return this.activeSwitch;
    }

    public void setSelectedButton(Switch s) {
        this.activeSwitch = s;
        s.isOn = true;
        for (Switch sw : this.switches) {
            if (sw != s) {
                sw.isOn = false;
            }
        }
    }
}

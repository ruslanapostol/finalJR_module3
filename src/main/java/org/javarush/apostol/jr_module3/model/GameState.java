package org.javarush.apostol.jr_module3.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GameState implements Serializable {
    private String currentStep = "start";
    private boolean gameEnded = false;

    public void reset() {
        this.currentStep = "start";
        this.gameEnded = false;
    }

    public void win() {
        this.gameEnded = true;
    }

    public void lose() {
        this.gameEnded = true;
    }
}

package org.javarush.apostol.jr_module3.model;

import java.io.Serializable;

public class GameState implements Serializable {
    private String currentStep = "start";

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public void reset() {
        this.currentStep = "start";
    }
}

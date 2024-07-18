package org.javarush.apostol.jr_module3.model;

import lombok.Getter;
import lombok.Setter;
import org.javarush.apostol.jr_module3.model.enums.GameStatus;
import java.io.Serializable;

@Getter
@Setter
public class GameState implements Serializable {
    private String currentStep = "start";
    private GameStatus status;

    public void reset() {
        this.currentStep = "start";
        this.status = GameStatus.IN_PROGRESS;
    }
}

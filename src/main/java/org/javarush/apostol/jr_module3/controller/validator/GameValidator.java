package org.javarush.apostol.jr_module3.controller.validator;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.javarush.apostol.jr_module3.model.GameState;
import java.util.Map;

@Getter
@Log4j2
public class GameValidator {
    private static GameValidator instance;

    private GameValidator() {}

    public static synchronized GameValidator getInstance() {
        if (instance == null) {
            instance = new GameValidator();
        }
        return instance;
    }

    public boolean isValidAnswer(String answer, Map<String, String> options) {
        return options != null && options.containsKey(answer);
    }

    public boolean isValidGameState(GameState gameState) {
        return gameState != null && gameState.getCurrentStep() != null;
    }
}

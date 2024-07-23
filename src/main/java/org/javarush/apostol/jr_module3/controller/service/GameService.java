package org.javarush.apostol.jr_module3.controller.service;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.javarush.apostol.jr_module3.model.GameState;
import org.javarush.apostol.jr_module3.model.GameStep;

import java.util.Map;
import java.util.Optional;

import static org.javarush.apostol.jr_module3.util.WebConstants.GAME_STATE;

@Log4j2
public class GameService {

    @Getter
    private static final GameService instance = new GameService();
    private final GameLogicService gameLogicService;

    private GameService() {
        gameLogicService = GameLogicService.getInstance();
    }

    public GameState createGameState() {
        return new GameState();
    }

    public GameState getOrCreateGameState(HttpSession session) {
        return Optional.ofNullable((GameState) session.getAttribute(GAME_STATE))
                .orElseGet(() -> {
                    GameState gameState = new GameState();
                    session.setAttribute(GAME_STATE, gameState);
                    return gameState;
                });
    }

    public void navigateGame(GameState gameState, String answer) {
        gameLogicService.getOptions(gameState.getCurrentStep())
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(answer))
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresentOrElse(nextStep -> {
                    GameStep nextGameStep = gameLogicService.getStep(nextStep);
                    gameState.setCurrentStep(nextStep);

                    if (nextGameStep.getEnd() != null) {
                        if (nextGameStep.getEnd().contains("Победа")) {
                            gameState.win();
                        } else {
                            gameState.lose();
                        }
                    }
                }, gameState::reset);
    }
}

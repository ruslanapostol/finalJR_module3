package org.javarush.apostol.jr_module3.controller.service;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.javarush.apostol.jr_module3.controller.validator.GameValidator;
import org.javarush.apostol.jr_module3.model.GameState;
import org.javarush.apostol.jr_module3.model.GameStep;

import java.util.Map;
import java.util.Optional;

import static org.javarush.apostol.jr_module3.util.WebConstants.GAME_STATE;

@Log4j2
public class GameService {

    @Getter
    private static final GameService instance = new GameService(GameLogicService.getInstance(),
            GameValidator.getInstance());
    private final GameLogicService gameLogicService;
    private final GameValidator gameValidator;

     GameService(GameLogicService gameLogicService, GameValidator gameValidator) {
       this.gameLogicService = gameLogicService;
       this.gameValidator = gameValidator;
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
        Map<String, String> options = gameLogicService.getOptions(gameState.getCurrentStep());
        if (options == null || !gameValidator.isValidAnswer(answer, options)) {
            gameState.reset();
            return;
        }
        operationsOnMappedValues(gameState, answer, options);
    }

    private void operationsOnMappedValues(GameState gameState, String answer, Map<String, String> options) {
        options.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(answer))
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresentOrElse(nextStep -> updateGameState(gameState, nextStep),
                        gameState::reset
                );
    }

    protected void updateGameState(GameState gameState, String nextStep) {
        log.debug("Updating game state to next step: {}", nextStep);
        GameStep nextGameStep = gameLogicService.getStep(nextStep);
        if (nextGameStep == null) {
            log.error("Next step is null for step ID: {}", nextStep);
            gameState.reset();
            return;
        }
        gameState.setCurrentStep(nextStep);

        if (nextGameStep.getEnd() != null) {
            gameState.setGameEnded(true);
        }

    }
}

package org.javarush.apostol.jr_module3.controller.service;

import jakarta.servlet.http.HttpSession;
import org.javarush.apostol.jr_module3.controller.validator.GameValidator;
import org.javarush.apostol.jr_module3.model.GameState;

import static org.junit.jupiter.api.Assertions.*;

import org.javarush.apostol.jr_module3.model.GameStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Map;

import static org.mockito.Mockito.*;


public class GameServiceTest {
    private GameService gameService;
    @Mock
    private GameLogicService gameLogicService;
    @Mock
    private GameValidator gameValidator;
    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService = new GameService(gameLogicService, gameValidator);
    }

    @Test
    void getOrCreateGameState_NewSession() {
        when(session.getAttribute("gameState")).thenReturn(null);
        GameState gameState = gameService.getOrCreateGameState(session);
        assertNotNull(gameState);
        verify(session).setAttribute("gameState", gameState);
    }

    @Test
    void getOrCreateGameState_ExistingSession() {
        GameState existingGameState = new GameState();
        when(session.getAttribute("gameState")).thenReturn(existingGameState);

        GameState gameState = gameService.getOrCreateGameState(session);

        assertSame(existingGameState, gameState);
        verify(session, never()).setAttribute(anyString(), any());
    }


    @Test
    void navigateGame_ValidAnswer() {
        GameState gameState = new GameState();
        gameState.setCurrentStep("start");

        Map<String, String> options = Map.of("validAnswer", "nextStep");
        when(gameLogicService.getOptions("start")).thenReturn(options);
        when(gameValidator.isValidAnswer("validAnswer", options)).thenReturn(true);

        GameStep nextStep = new GameStep();
        nextStep.setQuestion("Next step question");
        when(gameLogicService.getStep("nextStep")).thenReturn(nextStep);

        gameService.navigateGame(gameState, "validAnswer");

        assertEquals("nextStep", gameState.getCurrentStep());
    }

    @Test
    void navigateGame_InvalidAnswer() {
        GameState gameState = new GameState();
        gameState.setCurrentStep("start");

        Map<String, String> options = Map.of("invalidAnswer", "nextStep");
        when(gameLogicService.getOptions("start")).thenReturn(options);
        when(gameValidator.isValidAnswer("invalidAnswer", options)).thenReturn(false);

        gameService.navigateGame(gameState, "invalidAnswer");

        assertEquals("start", gameState.getCurrentStep());
    }

    @Test
    void updateGameState_NextStepIsEnd() {
        GameState gameState = new GameState();
        GameStep nextStep = new GameStep();
        nextStep.setEnd("end");

        when(gameLogicService.getStep("nextStep")).thenReturn(nextStep);

        gameService.updateGameState(gameState, "nextStep");

        assertTrue(gameState.isGameEnded());
    }

    @Test
    void updateGameState_NextStepIsNotEnd() {
        GameState gameState = new GameState();
        GameStep nextStep = new GameStep();
        nextStep.setQuestion("question");

        when(gameLogicService.getStep("nextStep")).thenReturn(nextStep);

        gameService.updateGameState(gameState, "nextStep");

        assertEquals("nextStep", gameState.getCurrentStep());
        assertFalse(gameState.isGameEnded());
    }

}

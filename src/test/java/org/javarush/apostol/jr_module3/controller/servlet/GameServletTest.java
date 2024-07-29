package org.javarush.apostol.jr_module3.controller.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.javarush.apostol.jr_module3.controller.service.GameLogicService;
import org.javarush.apostol.jr_module3.controller.service.GameService;
import org.javarush.apostol.jr_module3.model.GameState;
import org.javarush.apostol.jr_module3.model.GameStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.javarush.apostol.jr_module3.util.WebConstants.*;
import static org.mockito.Mockito.*;

class GameServletTest {

    @InjectMocks
    private GameServlet gameServlet;

    @Mock
    private GameService gameService;

    @Mock
    private GameLogicService gameLogicService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private GameState gameState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(GAME_JSP)).thenReturn(requestDispatcher);
        when(gameService.getOrCreateGameState(session)).thenReturn(gameState);
    }

    @Test
    void doGet_ShouldRedirectToIndexIfPlayerNameNotSet() throws IOException {
        // Arrange
        when(session.getAttribute(PLAYER_NAME)).thenReturn(null);

        // Act
        gameServlet.doGet(request, response);

        // Assert
        verify(response).sendRedirect(request.getContextPath() + INDEX_JSP);
    }

    @Test
    void doGet_ShouldForwardToGameJspIfPlayerNameSet() throws Exception {
        // Arrange
        when(session.getAttribute(PLAYER_NAME)).thenReturn(PLAYER_NAME);
        GameState gameState = new GameState();
        when(gameService.getOrCreateGameState(session)).thenReturn(gameState);

        GameStep gameStep = new GameStep();
        gameStep.setQuestion("Sample question");
        Map<String, String> options = new HashMap<>();
        options.put("option1", "Option 1");
        gameStep.setOptions(options);

        when(gameLogicService.getStep(gameState.getCurrentStep())).thenReturn(gameStep);

        // Act
        gameServlet.doGet(request, response);

        // Assert
        verify(request).setAttribute("question", "Sample question");
        verify(request).setAttribute("options", options);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPost_ShouldRedirectToIndexIfPlayerNameNotSet() throws IOException {
        // Arrange
        when(session.getAttribute(PLAYER_NAME)).thenReturn(null);

        // Act
        gameServlet.doPost(request, response);

        // Assert
        verify(response).sendRedirect(request.getContextPath() + INDEX_JSP);
    }

    @Test
    void doPost_ShouldProcessGameAndForwardToGameJspIfPlayerNameSet() throws Exception {
        // Arrange
        when(session.getAttribute(PLAYER_NAME)).thenReturn(PLAYER_NAME);
        when(request.getParameter("answer")).thenReturn("answer");

        GameState gameState = new GameState();
        when(gameService.getOrCreateGameState(session)).thenReturn(gameState);

        GameStep gameStep = new GameStep();
        gameStep.setQuestion("Sample question");
        Map<String, String> options = new HashMap<>();
        options.put("option1", "Option 1");
        gameStep.setOptions(options);

        when(gameLogicService.getStep(gameState.getCurrentStep())).thenReturn(gameStep);

        // Act
        gameServlet.doPost(request, response);

        // Assert
        verify(gameService).navigateGame(gameState, "answer");
        verify(request).setAttribute("question", "Sample question");
        verify(request).setAttribute("options", options);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPost_ShouldResetGameIfResetParameterIsTrue() throws IOException {
        // Arrange
        when(session.getAttribute(PLAYER_NAME)).thenReturn(PLAYER_NAME);
        when(request.getParameter("reset")).thenReturn("true");

        // Act
        gameServlet.doPost(request, response);

        // Assert
        verify(gameState).reset();
        verify(response).sendRedirect(request.getContextPath() + GAME);
    }
}
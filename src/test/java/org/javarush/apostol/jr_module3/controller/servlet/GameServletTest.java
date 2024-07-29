package org.javarush.apostol.jr_module3.controller.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.javarush.apostol.jr_module3.controller.service.GameLogicService;
import org.javarush.apostol.jr_module3.controller.service.GameService;
import org.javarush.apostol.jr_module3.model.GameState;
import org.javarush.apostol.jr_module3.model.GameStep;
import org.javarush.apostol.jr_module3.util.PlayerUtils;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.*;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameServletTest {

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

    private AutoCloseable closeableMocks;


    @BeforeEach
    void setUp() {
        closeableMocks = MockitoAnnotations.openMocks(this);
        gameServlet = new GameServlet();
        gameServlet.init();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeableMocks.close();
    }

    @Test
    void doGet_PlayerNameNotSet() throws IOException {
        when(PlayerUtils.ensurePlayerName(request, response, session)).thenReturn(false);

        gameServlet.doGet(request, response);

        Mockito.verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while" +
                " processing the game.");
    }

    @Test
    void doGet_PlayerNameSet() throws IOException {
        when(PlayerUtils.ensurePlayerName(request, response, session)).thenReturn(true);
        GameState gameState = new GameState();
        GameStep gameStep = new GameStep();
        gameState.setCurrentStep("start");

        when(request.getSession()).thenReturn(session);
        when(gameService.getOrCreateGameState(session)).thenReturn(gameState);
        when(gameLogicService.getStep("start")).thenReturn(gameStep);

        gameServlet.doGet(request, response);

        verify(request).setAttribute("question", gameStep.getQuestion());
        verify(request).setAttribute("options", gameStep.getOptions());
    }

    @Test
    void doPost_PlayerNameNotSet() throws IOException {
        when(PlayerUtils.ensurePlayerName(request, response, session)).thenReturn(false);

        gameServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while" +
                " processing the game.");
    }

    @Test
    void doPost_PlayerNameSet() throws IOException {
        when(PlayerUtils.ensurePlayerName(request, response, session)).thenReturn(true);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("reset")).thenReturn(null);

        GameState gameState = new GameState();
        gameState.setCurrentStep("start");

        GameStep gameStep = new GameStep();
        when(gameService.getOrCreateGameState(session)).thenReturn(gameState);
        when(gameLogicService.getStep("start")).thenReturn(gameStep);

        gameServlet.doPost(request, response);

        verify(request).setAttribute("question", gameStep.getQuestion());
        verify(request).setAttribute("options", gameStep.getOptions());
    }

    @Test
    void doPost_ResetGame () throws IOException {
        when(PlayerUtils.ensurePlayerName(request, response, session)).thenReturn(true);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("reset")).thenReturn("true");

        GameState gameState = new GameState();
        when(gameService.getOrCreateGameState(session)).thenReturn(gameState);

        gameServlet.doPost(request, response);

        verify(gameState).reset();
        verify(response).sendRedirect(anyString());
    }

}

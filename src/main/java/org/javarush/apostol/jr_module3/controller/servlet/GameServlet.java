package org.javarush.apostol.jr_module3.controller.servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.javarush.apostol.jr_module3.controller.service.QuestionService;
import org.javarush.apostol.jr_module3.controller.validator.GameValidator;
import org.javarush.apostol.jr_module3.model.GameState;
import org.javarush.apostol.jr_module3.model.GameStep;
import java.io.IOException;
import java.util.Map;
import static org.javarush.apostol.jr_module3.util.WebConstants.*;


@Log4j2
@WebServlet(GAME)
public class GameServlet extends HttpServlet {
    private GameValidator gameValidator;
    private QuestionService questionService;

    @Override
    public void init(ServletConfig config) {
        gameValidator = GameValidator.getInstance();
        questionService = QuestionService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameState gameState = getOrCreateGameState(session);

        if (!setPlayerNameIfNeeded(request, response, session)) {
            return;
        }

        GameStep stepData = questionService.getStep(gameState.getCurrentStep());
        setGameStepAttributes(request, stepData);

        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String answer = request.getParameter("answer");
        HttpSession session = request.getSession();
        GameState gameState = getOrCreateGameState(session);

        Map<String, String> stepOptions = questionService.getOptions(gameState.getCurrentStep());

        if (stepOptions != null && stepOptions.containsKey(answer)) {
            gameState.setCurrentStep(stepOptions.get(answer));
        } else {
            gameState.reset();
        }

        response.sendRedirect(GAME);
    }

    private GameState getOrCreateGameState(HttpSession session) {
        GameState gameState = (GameState) session.getAttribute("gameState");
        if (gameState == null) {
            gameState = new GameState();
            session.setAttribute("gameState", gameState);
        }
        return gameState;
    }

    private boolean setPlayerNameIfNeeded(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String playerName = (String) session.getAttribute("playerName");
        if (playerName == null) {
            playerName = request.getParameter("playerName");
            if (playerName != null && !playerName.trim().isEmpty()) {
                session.setAttribute("playerName", playerName);
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return false;
            }
        }
        return true;
    }

    private static void setGameStepAttributes(HttpServletRequest request, GameStep stepData) {
        request.setAttribute("question", stepData.getQuestion());
        request.setAttribute("options", stepData.getOptions());
        request.setAttribute("end", stepData.getEnd());
    }
}
package org.javarush.apostol.jr_module3.controller.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.javarush.apostol.jr_module3.controller.validator.GameValidator;
import org.javarush.apostol.jr_module3.exception.GameException;
import org.javarush.apostol.jr_module3.model.GameState;
import org.javarush.apostol.jr_module3.model.GameStep;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import static org.javarush.apostol.jr_module3.util.WebConstants.*;

@Log4j2
@WebServlet(GAME)
public class GameServlet extends HttpServlet {
    private Map<String, GameStep> gameLogic;
    private GameValidator gameValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gameValidator = GameValidator.getInstance();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("game_logic.json")) {
            ObjectMapper mapper = new ObjectMapper();
            gameLogic = mapper.readValue(input, new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error("Unable to load game logic", e);
            throw new ServletException("Unable to load game logic", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            GameState gameState = (GameState) session.getAttribute("gameState");

            if (!gameValidator.isValidGameState(gameState)) {
                throw new GameException("Invalid game state");
            }

            String currentStep = gameState.getCurrentStep();
            GameStep stepData = gameLogic.get(currentStep);

            setGameStepAttributes(request, stepData);

            request.getRequestDispatcher("/game.jsp").forward(request, response);
        } catch (GameException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        String answer = request.getParameter("answer");
        HttpSession session = request.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null) {
            gameState = new GameState();
            session.setAttribute("gameState", gameState);
        }

        GameStep stepData = gameLogic.get(gameState.getCurrentStep());
        Optional<Map<String, String>> optionsOpt = Optional.ofNullable(stepData.getOptions());

        if (optionsOpt.isPresent() && gameValidator.isValidAnswer(answer, optionsOpt.get())) {
            gameState.setCurrentStep(optionsOpt.get().get(answer));
        } else {
            gameState.reset();
        }

        response.sendRedirect(GAME);
    }

    private static void setGameStepAttributes(HttpServletRequest request, GameStep stepData) {
        Optional<String> question = Optional.ofNullable(stepData.getQuestion());
        Optional<Map<String, String>> options = Optional.ofNullable(stepData.getOptions());
        Optional<String> end = Optional.ofNullable(stepData.getEnd());

        question.ifPresent(q -> request.setAttribute("question", q));
        options.ifPresent(o -> request.setAttribute("options", o));
        end.ifPresent(e -> request.setAttribute("end", e));
    }
}

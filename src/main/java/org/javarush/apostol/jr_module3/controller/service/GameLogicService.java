package org.javarush.apostol.jr_module3.controller.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.javarush.apostol.jr_module3.exception.GameException;
import org.javarush.apostol.jr_module3.model.GameStep;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


@Log4j2
public class GameLogicService {
    @Getter
    private static final GameLogicService instance = new GameLogicService();
    private Map<String, GameStep> steps;

    private GameLogicService() {
        loadGameLogic();
    }

    private void loadGameLogic() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("game_logic.json")) {
            if (input == null) {
                log.error("game_logic.json not found");
                throw new GameException("game_logic.json not found");
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(input);
            steps = mapper.convertValue(rootNode.get("steps"), new TypeReference<>() {});

            log.info("Game logic loaded successfully");
            log.debug("Steps: {}", steps);
        } catch (IOException e) {
            log.error("Unable to load game logic", e);
            throw new GameException("Unable to load game logic");
        }
    }

    public GameStep getStep(String stepId) {
        log.debug("Getting step for ID: {}", stepId);
        GameStep step = steps.get(stepId);
        if (step == null) {
            log.error("Step not found for ID: {}", stepId);
        }
        return step;
    }

    public Map<String, String> getOptions(String stepId) {
        log.debug("Getting options for step ID: {}", stepId);
        GameStep step = steps.get(stepId);
        return (step != null) ? step.getOptions() : null;
    }
}


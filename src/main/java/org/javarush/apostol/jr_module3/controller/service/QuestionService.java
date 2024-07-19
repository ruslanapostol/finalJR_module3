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
public class QuestionService {
    private Map<String, GameStep> steps;
    private Map<String, Map<String, String>> options;


    @Getter
    private static final QuestionService instance = new QuestionService();

    private QuestionService() {
        loadGameLogic();
    }

    private void loadGameLogic() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("game_logic.json")) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(input);

            steps = mapper.convertValue(rootNode.get("steps"), new TypeReference<>() {
            });
            options = mapper.convertValue(rootNode.get("options"), new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error("Unable to load game logic", e);
            throw new GameException("Unable to load game logic");
        }
    }

    public GameStep getStep(String stepId) {
        return steps.get(stepId);
    }

    public Map<String, String> getOptions(String stepId) {
        return options.get(stepId);
    }
}

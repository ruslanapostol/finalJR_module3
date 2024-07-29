package org.javarush.apostol.jr_module3.controller.service;

import org.javarush.apostol.jr_module3.model.GameStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class GameLogicServiceTest {
    private GameLogicService gameLogicService;

    @BeforeEach
    public void setUp() {
        gameLogicService = GameLogicService.getInstance();
    }

    @Test
    public void testGetStep_ValidStartStep() {
        GameStep step = gameLogicService.getStep("start");
        assertNotNull(step,"Step should not be null");
        assertEquals("Ты потерял память. Принять вызов НЛО?", step.getQuestion());
        assertNotNull(step.getOptions(), "Options should not be null");
        assertTrue(step.getOptions().containsKey("Принять вызов"));
        assertTrue(step.getOptions().containsKey("Отклонить вызов"));
    }

    @Test
    public void testGetStep_ValidSecondStep() {
        GameStep step = gameLogicService.getStep("acceptChallenge");
        assertNotNull(step);
        assertEquals("Ты принял вызов. Поднимешься на мостик к капитану?", step.getQuestion());
        assertNotNull(step.getOptions());
        assertTrue(step.getOptions().containsKey("Подняться на мостик"));
        assertTrue(step.getOptions().containsKey("Отказаться подниматься на мостик"));
    }

    @Test
    public void testGetStep_ValidThirdStep() {
        GameStep step = gameLogicService.getStep("goToBridge");
        assertNotNull(step);
        assertEquals("Ты поднялся на мостик. Ты кто?", step.getQuestion());
        assertNotNull(step.getOptions());
        assertTrue(step.getOptions().containsKey("Рассказать правду о себе"));
        assertTrue(step.getOptions().containsKey("Солгать о себе"));
    }

    @Test
    public void testGetStep_ValidFourthStep() {
        GameStep step = gameLogicService.getStep("tellTruth");
        assertNotNull(step);
        assertEquals("Тебя вернули домой. Но что дальше?", step.getQuestion());
        assertNotNull(step.getOptions());
        assertTrue(step.getOptions().containsKey("Попытаться вспомнить прошлое"));
        assertTrue(step.getOptions().containsKey("Начать новую жизнь"));
    }

    @Test
    public void testGetStep_ValidLastStep() {
        GameStep step = gameLogicService.getStep("startNewLife");
        assertNotNull(step, "Step should not be null");
        assertEquals("Ты начал новую жизнь, свободную от прошлого. Победа.", step.getEnd());
    }

    @Test
    public void testGetStep_InvalidStepId() {
        GameStep step = gameLogicService.getStep("invalidStepId");
        assertNull(step);
    }

    @Test
    public void testGetOptions_ValidStartStep() {
        Map<String, String> options = gameLogicService.getOptions("start");
        assertNotNull(options);
        assertTrue(options.containsKey("Принять вызов"));
        assertTrue(options.containsKey("Отклонить вызов"));
    }

    @Test
    public void testGetOptions_ValidSecondStep() {
        Map<String, String> options = gameLogicService.getOptions("acceptChallenge");
        assertNotNull(options);
        assertTrue(options.containsKey("Подняться на мостик"));
        assertTrue(options.containsKey("Отказаться подниматься на мостик"));
    }

    @Test
    public void testGetOptions_ValidThirdStep() {
        Map<String, String> options = gameLogicService.getOptions("goToBridge");
        assertNotNull(options);
        assertTrue(options.containsKey("Рассказать правду о себе"));
        assertTrue(options.containsKey("Солгать о себе"));
    }

    @Test
    public void testGetOptions_ValidFourthStep() {
        Map<String, String> options = gameLogicService.getOptions("tellTruth");
        assertNotNull(options);
        assertTrue(options.containsKey("Попытаться вспомнить прошлое"));
        assertTrue(options.containsKey("Начать новую жизнь"));
    }

    @Test
    public void testGetOptions_InvalidStepId() {
        Map<String, String> options = gameLogicService.getOptions("invalidStepId");
        assertNull(options);
    }
}

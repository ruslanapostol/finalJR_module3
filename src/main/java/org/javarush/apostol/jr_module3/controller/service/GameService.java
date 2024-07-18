package org.javarush.apostol.jr_module3.controller.service;

import lombok.extern.log4j.Log4j2;
import org.javarush.apostol.jr_module3.model.GameState;

@Log4j2
public class GameService {
    private static GameService instance;

    private GameService() {
        log.info("Private constructor to prevent instantiation of GameService");
    }

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    public GameState createGameState() {
        return new GameState();
    }
}

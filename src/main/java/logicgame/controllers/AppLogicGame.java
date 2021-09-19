package logicgame.controllers;

import logicgame.service.GameService;

public class AppLogicGame {

    private static final int SUBJECTS_NUM = 6;

    public static void main(String[] args) {
        GameService gameService = new GameService();
        gameService.start(SUBJECTS_NUM);
    }

}

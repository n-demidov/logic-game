package logicgame_1.controllers;

import logicgame_1.service.GameService;

public class AppLogicGame {

    private static final int SUBJECTS_NUM = 3;

    public static void main(String[] args) {
        GameService gameService = new GameService();
        gameService.start(SUBJECTS_NUM);
    }

}

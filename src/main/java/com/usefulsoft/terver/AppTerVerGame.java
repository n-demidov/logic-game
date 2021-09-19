package com.usefulsoft.terver;

import com.usefulsoft.terver.service.GameService;
import com.usefulsoft.terver.service.Randomizer;

public class AppTerVerGame {
    private static final Randomizer randomizer = new Randomizer();
    private static final GameService gameService = new GameService();

    public static void main(String[] args) {
        int figuresGroupsNum = randomizer.generateFromRange(Consts.MIN_FIGURE_TYPES, Consts.MAX_FIGURE_TYPES);
        gameService.generate(figuresGroupsNum);
    }

}

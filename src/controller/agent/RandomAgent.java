package controller.agent;

import model.Pair;
import model.Players;
import model.RowGameModel;

import java.util.ArrayList;

public class RandomAgent implements Agent {

    private final RowGameModel gameModel;
    private final Players player;

    public RandomAgent(RowGameModel gameModel, Players player) {
        this.gameModel = gameModel;
        this.player = player;
    }

    public Pair _move() {

        if (!gameModel.isGameEnd()) {
            ArrayList<Integer> choices = new ArrayList<>();
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (gameModel.getBlocksData()[r][c].getIsLegalMove()) {
                        choices.add(r * 3 + c);
                    }
                }
            }
            int index = choices.get((int) (Math.random() * choices.size()));
            return new Pair(index / 3, index % 3);
        }
        return null;
    }

    public RowGameModel getGameModel() {
        return gameModel;
    }

    public Players getPlayer() {
        return player;
    }
}

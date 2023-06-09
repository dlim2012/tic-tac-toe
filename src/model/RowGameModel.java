package model;

import java.util.Stack;

public class RowGameModel {
    public static final String GAME_END_NOWINNER = "Game ends in a draw";
    public static final String PLAYER1_WINS = "Player 1 wins!";
    public static final String PLAYER2_WINS = "Player 2 wins!";

    private RowBlockModel[][] blocksData = new RowBlockModel[3][3];
    private Stack<Pair> history = new Stack<>();

    /**
     * The current player taking their turn
     */
    private Players player = Players.PLAYER1;
    private int movesLeft = 9;

    private String finalResult = null;

    private boolean gameEnd = false;


    public RowGameModel() {
        super();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                blocksData[row][col] = new RowBlockModel(this);
            } // end for col
        } // end for row
    }

    public RowBlockModel[][] getBlocksData() {
        return blocksData;
    }

    public void setBlocksData(RowBlockModel[][] blocksData) {
        this.blocksData = blocksData;
    }

    public Stack<Pair> getHistory() {
        return history;
    }

    public void setHistory(Stack<Pair> history) {
        this.history = history;
    }

    public void increaseMovesLeft() {
        this.movesLeft++;
    }

    public void decreaseMovesLeft() {
        this.movesLeft--;
    }

    public boolean hasMovesLeft() {
        return this.movesLeft > 0;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    public String getFinalResult() {
        return this.finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    public Players getPlayer() {
        return player;
    }

    public void setPlayer(Players player) {
        this.player = player;
    }

    public void nextPlayer() {
        this.player = this.player.next();
    }

    public void prevPlayer() {
        this.player = this.player.prev();
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }
}

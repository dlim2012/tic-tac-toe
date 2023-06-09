package controller;

import controller.agent.Agent;
import controller.agent.NoAgent;
import model.Pair;
import model.Players;
import model.RowGameModel;
import view.RowGameGUI;

public class RowGameController {

    private final RowGameModel gameModel;
    private final RowGameGUI gameView;

    private Agent agent;

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameController() {
        gameModel = new RowGameModel();
        gameView = new RowGameGUI(this);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                gameModel.getBlocksData()[row][column].setContents("");
                gameModel.getBlocksData()[row][column].setIsLegalMove(true);
                gameView.update(gameModel);
            }
        }

        agent = new NoAgent();
    }

    /* Check if game ended by row */
    private boolean checkRowWin(int row, String content) {
        for (int i = 0; i < 3; i++) {
            if (!gameModel.getBlocksData()[row][i].getContents().equals(content)) {
                return false;
            }
        }
        return true;
    }

    /* Check if game ended by column */
    private boolean checkColumnWin(int column, String content) {
        for (int i = 0; i < 3; i++) {
            if (!gameModel.getBlocksData()[i][column].getContents().equals(content)) {
                return false;
            }
        }
        return true;
    }

    /* Check if game ended by diagonal (row = column) */
    private boolean checkDiagWin1(int row, int column, String content) {
        if (row != column) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if (!gameModel.getBlocksData()[i][i].getContents().equals(content)) {
                return false;
            }
        }
        return true;
    }


    /* Check if game ended by diagonal (row + column == 2) */
    public boolean checkDiagWin2(int row, int column, String content) {
        if (row + column != 2) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if (!gameModel.getBlocksData()[i][2 - i].getContents().equals(content)) {
                return false;
            }
        }
        return true;
    }

    /* Check if there is a winner */
    public boolean checkWin(int row, int column, String content) {
        return this.checkRowWin(row, content) || this.checkColumnWin(column, content) ||
                this.checkDiagWin1(row, column, content) || this.checkDiagWin2(row, column, content);
    }


    public void _move(int row, int column) {
        if (row < 0 || row >= 3 || column < 0 || column >= 3) {
            throw new IllegalArgumentException("Invalid row or column.");
        }
        if (!gameModel.getBlocksData()[row][column].getIsLegalMove()) {
            return;
        }
        String content = gameModel.getPlayer() == Players.PLAYER1 ? "X" : "O";
        gameModel.getBlocksData()[row][column].setContents(content);
        gameModel.getBlocksData()[row][column].setIsLegalMove(false);
        gameModel.decreaseMovesLeft();

        if (this.checkWin(row, column, content)) {
            gameModel.setFinalResult(
                    gameModel.getPlayer() == Players.PLAYER1 ? RowGameModel.PLAYER1_WINS :
                            RowGameModel.PLAYER2_WINS);
            endGame();
        } else if (!gameModel.hasMovesLeft()) {
            gameModel.setFinalResult(RowGameModel.GAME_END_NOWINNER);
        }

        gameModel.getHistory().add(new Pair(row, column));
        gameView.update(gameModel);
        gameModel.nextPlayer();
    }

    public void move(int row, int column) {
        _move(row, column);
        Pair agentMove = agent._move();
        if (agentMove != null) {
            _move(agentMove.getRow(), agentMove.getCol());
        }
    }


    /**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                gameModel.getBlocksData()[row][column].setIsLegalMove(false);
            }
        }
        gameModel.setGameEnd(true);
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                gameModel.getBlocksData()[row][column].reset();
                gameModel.getBlocksData()[row][column].setIsLegalMove(true);
            }
        }
        gameModel.setPlayer(Players.PLAYER1);
        gameModel.setMovesLeft(9);
        gameModel.setFinalResult(null);
        gameModel.setGameEnd(false);
        gameModel.getHistory().clear();
        gameView.update(gameModel);
    }

    public void _undo() {
        if (!gameModel.getHistory().isEmpty()) {
            Pair p = gameModel.getHistory().pop();
            gameModel.increaseMovesLeft();
            gameModel.getBlocksData()[p.getRow()][p.getCol()].reset();
            if (gameModel.getFinalResult() != null) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        gameModel.getBlocksData()[i][j].setIsLegalMove(gameModel.getBlocksData()[i][j].getContents().isEmpty());
                    }
                }
                gameModel.setFinalResult(null);
            }
            gameModel.prevPlayer();
            gameModel.setGameEnd(false);
            gameView.update(gameModel);
        }
    }

    public void undo() {
        _undo();
        if (!gameModel.getHistory().empty() && gameModel.getPlayer() == agent.getPlayer()) {
            _undo();
        }
    }

    public RowGameModel getGameModel() {
        return gameModel;
    }

    public RowGameGUI getGameView() {
        return gameView;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

}

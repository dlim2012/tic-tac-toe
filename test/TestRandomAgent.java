import controller.RowGameController;
import controller.agent.RandomAgent;
import model.Pair;
import model.Players;
import view.MessageView;
import view.PanelView;
import view.ResetButtonView;
import view.UndoButtonView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestRandomAgent {

    private RowGameController game;

    @Before
    public void setUp() {
        game = new RowGameController();
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void testLegalMove() {
        // Check that the block was empty before making move
        Pair firstMove = new Pair(1, 1);

        game.setAgent(new RandomAgent(game.getGameModel(), Players.PLAYER2));

        game.move(firstMove.getRow(), firstMove.getCol());

        int player2Moves = 0;


        for (int row=0; row<3; row++){
            for (int col=0; col<3; col++){
                if (game.getGameModel().getBlocksData()[row][col].getContents().equals("O")){
                    player2Moves += 1;
                    assertFalse (game.getGameModel().getBlocksData()[row][col].getIsLegalMove());
                } else if (row == firstMove.getRow() && col == firstMove.getCol()) {
                    assertTrue (game.getGameModel().getBlocksData()[row][col].getContents().equals("X"));
                    assertFalse (game.getGameModel().getBlocksData()[row][col].getIsLegalMove());
                } else {
                    assertTrue (game.getGameModel().getBlocksData()[row][col].getIsLegalMove());
                }
            }
        }

        assertTrue (player2Moves == 1);
        assertTrue (game.getGameModel().getMovesLeft() == 7);
        assertTrue (game.getGameModel().getPlayer() == Players.PLAYER1);
        assertTrue (game.getGameModel().getFinalResult() == null);
        assertFalse (game.getGameModel().isGameEnd());

        // Check views: panel
        for (int row=0; row<3; row++){
            for (int column=0; column<3; column++){
                assertEquals(game.getGameModel().getBlocksData()[row][column].getIsLegalMove(),
                        ((PanelView) game.getGameView().getViews().get("panel")).getBlocks()[row][column].isEnabled());
                assertEquals(game.getGameModel().getBlocksData()[row][column].getContents(),
                        ((PanelView) game.getGameView().getViews().get("panel")).getBlocks()[row][column].getText());
            }
        }

        // Check views: message
        assertEquals ("'X': Player 1",
                ((MessageView) game.getGameView().getViews().get("message")).getPlayerturn().getText());

        // Check views: reset button
        assertTrue (((ResetButtonView) game.getGameView().getViews().get("reset")).getReset().isEnabled());

        // Check views: undo button
        assertTrue (((UndoButtonView) game.getGameView().getViews().get("undo")).getUndo().isEnabled());


        // test undo
        game.undo();

        for (int row=0; row<3; row++){
            for (int col=0; col<3; col++){
                assertTrue (game.getGameModel().getBlocksData()[row][col].getIsLegalMove());
                assertTrue (game.getGameModel().getBlocksData()[row][col].getContents().isEmpty());
            }
        }

        assertTrue (game.getGameModel().getMovesLeft() == 9);
        assertTrue (game.getGameModel().getPlayer() == Players.PLAYER1);
        assertTrue (game.getGameModel().getFinalResult() == null);
        assertFalse (game.getGameModel().isGameEnd());
    }
}

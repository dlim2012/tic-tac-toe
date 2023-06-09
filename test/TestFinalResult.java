import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowGameModel;
import controller.RowGameController;
import view.MessageView;
import view.PanelView;
import view.ResetButtonView;
import view.UndoButtonView;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestFinalResult {
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
    public void testPlayer1Wins() {
        // Make some moves such that the game didn't end and a player can win in the next move
        game.move(0, 0);
        game.move(1, 0);
        game.move(0, 1);
        game.move(1,1);

        // Check final result before game end
        assertNull (game.getGameModel().getFinalResult());

        // Player1 should win after this move
        game.move(0, 2);

        // Check that the final result has changed correctly
        assertEquals("Player 1 wins!", game.getGameModel().getFinalResult());

        // Check that illegal moves of blocks are set correctly
        for(int row = 0;row<3;row++) {
            for(int column = 0;column<3;column++) {
                assertFalse(game.getGameModel().getBlocksData()[row][column].getIsLegalMove());
            }
        }

        // Check views: panel
        for (int row=0; row<3; row++){
            for (int column=0; column<3; column++){
                assertFalse(((PanelView) game.getGameView().getViews().get("panel")).getBlocks()[row][column].isEnabled());
            }
        }

        // Check views: message
        assertEquals ("Player 1 wins!",
                ((MessageView) game.getGameView().getViews().get("message")).getPlayerturn().getText());

        // Check views: reset button
        assertTrue (((ResetButtonView) game.getGameView().getViews().get("reset")).getReset().isEnabled());

        // Check views: undo button
        assertTrue (((UndoButtonView) game.getGameView().getViews().get("undo")).getUndo().isEnabled());
    }

    @Test
    public void testPlayer2Wins() {
        // Make some moves such that the game didn't end and a player can win in the next move
        game.move(0, 0);
        game.move(1, 0);
        game.move(0, 1);
        game.move(1,1);
        game.move(2, 2);

        // Check final result before game end
        assertNull (game.getGameModel().getFinalResult());

        // Player1 should win after this move
        game.move(1, 2);

        // Check that the final result has changed correctly
        assertEquals("Player 2 wins!", game.getGameModel().getFinalResult());

        // Check that illegal moves of blocks are set correctly
        for(int row = 0;row<3;row++) {
            for(int column = 0;column<3;column++) {
                assertFalse(game.getGameModel().getBlocksData()[row][column].getIsLegalMove());
            }
        }

        // Check views: panel
        for (int row=0; row<3; row++){
            for (int column=0; column<3; column++){
                assertFalse(((PanelView) game.getGameView().getViews().get("panel")).getBlocks()[row][column].isEnabled());
            }
        }

        // Check views: message
        assertEquals ("Player 2 wins!",
                ((MessageView) game.getGameView().getViews().get("message")).getPlayerturn().getText());

        // Check views: reset button
        assertTrue (((ResetButtonView) game.getGameView().getViews().get("reset")).getReset().isEnabled());

        // Check views: undo button
        assertTrue (((UndoButtonView) game.getGameView().getViews().get("undo")).getUndo().isEnabled());


    }
    @Test
    public void testUpdateTie() {
        // Make move such that the game will end in a tie in the next move
        game.move(0, 0);
        game.move(0, 1);
        game.move(0, 2);
        game.move(2, 0);
        game.move(2, 1);
        game.move(2, 2);
        game.move(1, 0);
        game.move(1, 1);

        // Check final result before game end
        assertNull (game.getGameModel().getFinalResult());

        // The game should end in a tie after this move
        game.move(1, 2);

        // Check that the final result has changed correctly
        assertEquals(RowGameModel.GAME_END_NOWINNER, game.getGameModel().getFinalResult());

        // Check that illegal moves of blocks are set correctly
        for(int row = 0;row<3;row++) {
            for(int column = 0;column<3;column++) {
                assertFalse(game.getGameModel().getBlocksData()[row][column].getIsLegalMove());
            }
        }

        // Check views: panel
        for (int row=0; row<3; row++){
            for (int column=0; column<3; column++){
                assertFalse(((PanelView) game.getGameView().getViews().get("panel")).getBlocks()[row][column].isEnabled());
            }
        }

        // Check views: message
        assertEquals ("Game ends in a draw",
                ((MessageView) game.getGameView().getViews().get("message")).getPlayerturn().getText());

        // Check views: reset button
        assertTrue (((ResetButtonView) game.getGameView().getViews().get("reset")).getReset().isEnabled());

        // Check views: undo button
        assertTrue (((UndoButtonView) game.getGameView().getViews().get("undo")).getUndo().isEnabled());
    }
}

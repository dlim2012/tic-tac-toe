import model.Players;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import controller.RowGameController;
import org.w3c.dom.ls.LSOutput;
import view.MessageView;
import view.PanelView;
import view.ResetButtonView;
import view.UndoButtonView;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestGameReset {
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
    public void testReset() {
        // make some moves before reset
        for (int i=0; i<3; i++) {
            game.move(i, i);
        }

        // test pre-conditions
        for (int i=0; i<3; i++) {
            assertFalse (game.getGameModel().getBlocksData()[i][i].getIsLegalMove());
            assertFalse (game.getGameModel().getBlocksData()[i][i].getContents().isEmpty());
        }

        // reset the game
        game.resetGame();

        // test expected game status
        assertNull (game.getGameModel().getFinalResult());
        assertEquals (Players.PLAYER1, game.getGameModel().getPlayer());
        assertEquals (9, game.getGameModel().getMovesLeft());
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                assertTrue(game.getGameModel().getBlocksData()[i][j].getContents().isEmpty());
                assertTrue(game.getGameModel().getBlocksData()[i][j].getIsLegalMove());
            }
        }
        assertTrue (game.getGameModel().getHistory().isEmpty());



        // Check views: panel
        for (int row=0; row<3; row++){
            for (int column=0; column<3; column++){
                assertTrue(((PanelView) game.getGameView().getViews().get("panel")).getBlocks()[row][column].isEnabled());
                assertEquals("", ((PanelView) game.getGameView().getViews().get("panel")).getBlocks()[row][column].getText());
            }
        }

        // Check views: message
        assertEquals ("'X': Player 1",
                ((MessageView) game.getGameView().getViews().get("message")).getPlayerturn().getText());

        // Check views: reset button
        assertTrue (((ResetButtonView) game.getGameView().getViews().get("reset")).getReset().isEnabled());

        // Check views: undo button
        assertFalse (((UndoButtonView) game.getGameView().getViews().get("undo")).getUndo().isEnabled());
    }

}

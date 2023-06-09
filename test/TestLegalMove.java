import model.Players;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import model.Pair;
import controller.RowGameController;
import view.MessageView;
import view.PanelView;
import view.ResetButtonView;
import view.UndoButtonView;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestLegalMove {
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
        assertTrue (game.getGameModel().getBlocksData()[1][1].getContents().isEmpty());
        assertTrue (game.getGameModel().getBlocksData()[1][1].getIsLegalMove());

        // record contents before the move
        Players player = game.getGameModel().getPlayer();
        int movesLeft = game.getGameModel().getMovesLeft();


        game.move(1, 1);

        // Check the status of the block
        assertFalse (game.getGameModel().getBlocksData()[1][1].getContents().isEmpty());
        assertFalse (game.getGameModel().getBlocksData()[1][1].getIsLegalMove());

        // Check if the player and movesLeft fields of RowGameModel have changed
        assertNotEquals (player, game.getGameModel().getPlayer());
        assertEquals (movesLeft - 1, game.getGameModel().getMovesLeft());
        assertEquals (1, game.getGameModel().getHistory().lastElement().getRow());
        assertEquals (1, game.getGameModel().getHistory().lastElement().getCol());


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
        assertEquals ("'O': Player 2",
                ((MessageView) game.getGameView().getViews().get("message")).getPlayerturn().getText());

        // Check views: reset button
        assertTrue (((ResetButtonView) game.getGameView().getViews().get("reset")).getReset().isEnabled());

        // Check views: undo button
        assertTrue (((UndoButtonView) game.getGameView().getViews().get("undo")).getUndo().isEnabled());
    }

}

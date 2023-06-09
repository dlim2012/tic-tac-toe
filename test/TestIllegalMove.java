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
public class TestIllegalMove {
    private RowGameController game;

    @Before
    public void setUp() {
        // Make a new game and make two moves for the pre-condition of testing
        game = new RowGameController();
        game.move(0, 0);
        game.move(1, 1);
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void testIllegalMove() {

        // Test if the isLegalMove field of RowBlockModel instances are set correctly
        assertFalse (game.getGameModel().getBlocksData()[0][0].getIsLegalMove());
        assertFalse (game.getGameModel().getBlocksData()[1][1].getIsLegalMove());

        // Save the states before the test
        String content = game.getGameModel().getBlocksData()[1][1].getContents();
        int movesLeft = game.getGameModel().getMovesLeft();
        String finalResult = game.getGameModel().getFinalResult();
        Players player = game.getGameModel().getPlayer();
        int historySize = game.getGameModel().getHistory().size();
        Pair pair = game.getGameModel().getHistory().lastElement();

        // Make an illegal move
        game.move(1, 1);

        // The states should not have changed after the illegal move
        assertEquals (content, game.getGameModel().getBlocksData()[1][1].getContents());
        assertEquals (movesLeft, game.getGameModel().getMovesLeft());
        assertEquals (finalResult, game.getGameModel().getFinalResult());
        assertEquals (player, game.getGameModel().getPlayer());
        assertFalse (game.getGameModel().getBlocksData()[1][1].getIsLegalMove());
        assertEquals (historySize, game.getGameModel().getHistory().size());
        assertEquals (pair, game.getGameModel().getHistory().lastElement());

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
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMoveInput(){
        game.move(3, 2);
    }

}

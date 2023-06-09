import model.Pair;
import model.Players;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import controller.RowGameController;
import view.MessageView;
import view.PanelView;
import view.ResetButtonView;
import view.UndoButtonView;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestUndo {
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
    public void TestUndoBeforeGameEnd() {
        game.move(0, 0);
        game.move(1, 1);
        game.move(2, 2);

        // Check conditions before test
        assertFalse (game.getGameModel().getBlocksData()[2][2].getContents().isEmpty());
        assertFalse (game.getGameModel().getBlocksData()[2][2].getIsLegalMove());
        assertEquals (6, game.getGameModel().getMovesLeft());
        assertEquals (Players.PLAYER2, game.getGameModel().getPlayer());
        assertEquals (3, game.getGameModel().getHistory().size());
        assertEquals (2, game.getGameModel().getHistory().lastElement().getRow());
        assertEquals (2, game.getGameModel().getHistory().lastElement().getCol());

        // undo last move
        game.undo();

        // Check the status after undo
        assertTrue (game.getGameModel().getBlocksData()[2][2].getContents().isEmpty());
        assertTrue (game.getGameModel().getBlocksData()[2][2].getIsLegalMove());
        assertEquals (7, game.getGameModel().getMovesLeft());
        assertEquals (Players.PLAYER1, game.getGameModel().getPlayer());
        assertEquals (2, game.getGameModel().getHistory().size());
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
        assertEquals ("'X': Player 1",
                ((MessageView) game.getGameView().getViews().get("message")).getPlayerturn().getText());

        // Check views: reset button
        assertTrue (((ResetButtonView) game.getGameView().getViews().get("reset")).getReset().isEnabled());

        // Check views: undo button
        assertTrue (((UndoButtonView) game.getGameView().getViews().get("undo")).getUndo().isEnabled());

    }

    @Test
    public void TestUndoAfterGameEnd() {
        game.move(0, 0);
        game.move(1, 0);
        game.move(0, 1);
        game.move(1,1);
        game.move(0, 2);

        // Check conditions before test
        assertFalse (game.getGameModel().getBlocksData()[0][2].getContents().isEmpty());
        for (int row=0; row<3; row++){
            for (int column=0; column<3; column++){
                assertFalse (game.getGameModel().getBlocksData()[row][column].getIsLegalMove());
            }
        }
        assertEquals (4, game.getGameModel().getMovesLeft());
        assertEquals (Players.PLAYER2, game.getGameModel().getPlayer());
        assertEquals (5, game.getGameModel().getHistory().size());
        assertEquals (0, game.getGameModel().getHistory().lastElement().getRow());
        assertEquals (2, game.getGameModel().getHistory().lastElement().getCol());

        // undo last move
        game.undo();

        // Check the status after undo
        assertTrue (game.getGameModel().getBlocksData()[0][2].getContents().isEmpty());
        assertTrue (game.getGameModel().getBlocksData()[0][2].getIsLegalMove());
        assertEquals (5, game.getGameModel().getMovesLeft());
        assertEquals (Players.PLAYER1, game.getGameModel().getPlayer());
        assertEquals (4, game.getGameModel().getHistory().size());
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
        assertEquals ("'X': Player 1",
                ((MessageView) game.getGameView().getViews().get("message")).getPlayerturn().getText());

        // Check views: reset button
        assertTrue (((ResetButtonView) game.getGameView().getViews().get("reset")).getReset().isEnabled());

        // Check views: undo button
        assertTrue (((UndoButtonView) game.getGameView().getViews().get("undo")).getUndo().isEnabled());

    }
}

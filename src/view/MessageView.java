package view;

import model.RowGameModel;

import javax.swing.*;

public class MessageView implements View {
    private final JTextArea playerturn;
    private final String GAME_START = "Player 1 to play 'X'";

    public MessageView(JTextArea message) {
        this.playerturn = message;
        playerturn.setText(GAME_START);
        playerturn.setEditable(false);
    }

    @Override
    public void update(RowGameModel model) {

        if (model.getFinalResult() != null) {
            playerturn.setText(model.getFinalResult());
        } else if (model.getMovesLeft() % 2 == 1) {
            playerturn.setText("'X': Player 1");
        } else {
            playerturn.setText("'O': Player 2");
        }
    }

    public JTextArea getPlayerturn() {
        return playerturn;
    }
}

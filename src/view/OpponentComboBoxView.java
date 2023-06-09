package view;

import controller.RowGameController;
import controller.agent.NoAgent;
import controller.agent.RandomAgent;
import model.Players;
import model.RowGameModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpponentComboBoxView implements View {
    private final JComboBox opponentComboBox;

    public OpponentComboBoxView(JComboBox opponentComboBox, RowGameController controller) {
        this.opponentComboBox = opponentComboBox;
        opponentComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = (String) opponentComboBox.getSelectedItem();
                assert s != null;
                if (s.equals("Human")) {
                    controller.setAgent(new NoAgent());
                } else if (s.equals("Random")) {
                    controller.setAgent(new RandomAgent(controller.getGameModel(), Players.PLAYER2));
                }
            }
        });
    }

    @Override
    public void update(RowGameModel model) {
    }

    public JComboBox getOpponentComboBox() {
        return opponentComboBox;
    }
}

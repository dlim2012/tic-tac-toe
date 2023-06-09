package view;

import controller.RowGameController;
import model.RowGameModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetButtonView implements View {
    private final JButton resetButton;

    public ResetButtonView(JButton resetButton, RowGameController controller) {
        this.resetButton = resetButton;
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
            }
        });
    }

    @Override
    public void update(RowGameModel model) {
    }

    public JButton getReset() {
        return resetButton;
    }
}

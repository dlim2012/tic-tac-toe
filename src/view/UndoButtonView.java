package view;

import controller.RowGameController;
import model.RowGameModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoButtonView implements View {
    private JButton undoButton = new JButton("undo");

    public UndoButtonView(JButton undoButton, RowGameController controller) {
        this.undoButton = undoButton;
        undoButton.setFocusPainted(false);
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.undo();
            }
        });
    }

    @Override
    public void update(RowGameModel model) {
        undoButton.setEnabled(!model.getHistory().empty());
    }

    public JButton getUndo() {
        return undoButton;
    }
}

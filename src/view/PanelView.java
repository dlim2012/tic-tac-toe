package view;

import controller.RowGameController;
import model.RowGameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelView implements View {
    private final JButton[][] blocks = new JButton[3][3];

    public PanelView(JPanel game, RowGameController controller) {

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                blocks[row][column] = new JButton();
                blocks[row][column].setPreferredSize(new Dimension(75, 75));
                blocks[row][column].setFocusPainted(false);
                game.add(blocks[row][column]);
                int finalRow = row;
                int finalColumn = column;
                blocks[row][column].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        controller.move(finalRow, finalColumn);
                    }
                });
            }
        }
    }

    @Override
    public void update(RowGameModel model) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                blocks[row][column].setText(model.getBlocksData()[row][column].getContents());
                blocks[row][column].setEnabled(model.getBlocksData()[row][column].getIsLegalMove());
            }
        }
    }

    public JButton[][] getBlocks() {
        return blocks;
    }
}

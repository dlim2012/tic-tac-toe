package view;

import controller.RowGameController;
import model.RowGameModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RowGameGUI implements View {
    private JFrame gui = new JFrame("Tic Tac Toe");
    private Map<String, View> views = new HashMap();


    public RowGameGUI(RowGameController controller) {
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension(500, 350));
        gui.setResizable(true);

        JPanel northPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        String[] opponentOptions = {"Human", "Random"};
        JLabel opponentLabel = new JLabel("Choose opponent:");
        JComboBox opponentComboBox = new JComboBox(opponentOptions);
        JButton resetButton = new JButton("Reset");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        northPanel.add(opponentLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        northPanel.add(opponentComboBox, c);
        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        northPanel.add(gamePanel, c);
        gui.add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        JButton undoButton = new JButton("undo");
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        centerPanel.add(undoButton, c);
        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(resetButton, c);
        gui.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout());
        JTextArea message = new JTextArea();
        southPanel.setBackground(Color.white);
        southPanel.add(message);
        gui.add(southPanel, BorderLayout.SOUTH);

        View panelView = new PanelView(gamePanel, controller);
        View comboButtonView = new OpponentComboBoxView(opponentComboBox, controller);
        View resetButtonView = new ResetButtonView(resetButton, controller);
        View undoButtonView = new UndoButtonView(undoButton, controller);
        View messageView = new MessageView(message);

        this.views.put("panel", panelView);
        this.views.put("combo", comboButtonView);
        this.views.put("reset", resetButtonView);
        this.views.put("undo", undoButtonView);
        this.views.put("message", messageView);

    }

    @Override
    public void update(RowGameModel model) {
        for (View view : this.views.values()) {
            view.update(model);
        }
    }

    public JFrame getGui() {
        return gui;
    }

    public void setGui(JFrame gui) {
        this.gui = gui;
    }

    public Map<String, View> getViews() {
        return views;
    }

    public void setViews(Map<String, View> views) {
        this.views = views;
    }


}

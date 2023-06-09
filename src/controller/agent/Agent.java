package controller.agent;

import model.Pair;
import model.Players;

public interface Agent {

    Pair _move();

    Players getPlayer();
}

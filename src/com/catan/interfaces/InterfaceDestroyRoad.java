package com.catan.interfaces;

import com.catan.modal.Player;
import com.catan.modal.Road;

import java.util.ArrayList;

public interface InterfaceDestroyRoad {
    void destroyRoad(Road road, Player currentPlayer, ArrayList<Player> allPlayers);
}

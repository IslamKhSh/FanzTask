package com.fanz.task.main;

import com.fanz.task.entity.Player;

import java.util.ArrayList;

public interface MainView {
    void onPlayerClicked(int index);

    void onGetPlayers(ArrayList<Player> players);
}

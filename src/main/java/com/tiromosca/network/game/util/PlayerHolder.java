package com.tiromosca.network.game.util;

import com.tiromosca.network.connection.client.PlayerClient;

public class PlayerHolder {
    private PlayerClient player;

    private final static PlayerHolder INSTANCE = new PlayerHolder();

    private PlayerHolder() {
    }

    public static PlayerHolder getInstance() {
        return INSTANCE;
    }

    public void setPlayer(PlayerClient playerClient) {
        this.player = playerClient;
    }

    public PlayerClient getPlayer() {
        return this.player;
    }
}

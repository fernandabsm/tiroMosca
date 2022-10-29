package com.tiromosca.network.singleplayer.util;

import com.tiromosca.network.singleplayer.SmartGame;

public class SmartGameHolder {

    private SmartGame smartGame;

    private final static SmartGameHolder INSTANCE = new SmartGameHolder();

    private SmartGameHolder() {
    }

    public static SmartGameHolder getInstance() {
        return INSTANCE;
    }

    public void setSmartGame(SmartGame smartGame) {
        this.smartGame = smartGame;
    }

    public SmartGame getSmartGame() {
        return this.smartGame;
    }
}

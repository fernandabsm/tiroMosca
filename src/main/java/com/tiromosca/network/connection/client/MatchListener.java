package com.tiromosca.network.connection.client;

public interface MatchListener {

    void haveAResult (String flies, String shots, int winner, int looser);
}

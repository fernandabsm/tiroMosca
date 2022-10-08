package com.tiromosca.network.connection.client;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;

@Data
public class PlayerClient {

    public PlayerClient(NetworkListener networkListener) {
        this.listener = networkListener;
    }

    private PlayerClientConnection playerClientConnection;
    private int opponentID;
    private int playerID;

    private Boolean itsMyTimeToPlay;

    private NetworkListener listener;

    public void sendAim(String aim) { playerClientConnection.sendAim(aim); }

    public void connectToServer() {
        playerClientConnection = new PlayerClientConnection();
        playerID = playerClientConnection.getPlayerID();
    }

    public void verifyOpponentConnection() {
        System.out.println("Entrei nessa função");
        playerClientConnection.verifyOpponentConnection();
    }

    @Data
    private class PlayerClientConnection {
        private Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private int opponentID;
        private int playerID;

        public PlayerClientConnection() {
            try {
                this.socket = new Socket("localhost", 20525);
                var outputStream = new OutputStreamWriter(socket.getOutputStream());
                var inputStream = new InputStreamReader(socket.getInputStream());
                this.bufferedWriter = new BufferedWriter(outputStream);
                this.bufferedReader = new BufferedReader(inputStream);
                this.playerID = bufferedReader.read();
                System.out.println("Player " + playerID + " conectado");
                this.opponentID = (playerID == 1) ? 2 : 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void verifyOpponentConnection() {
            System.out.println("Entrei na função");
            new Thread(() -> {
                String msgFromOpponent = StringUtils.EMPTY;

                System.out.println("Vou entrar no while... aguardando...");
                while (msgFromOpponent.isEmpty()) {
                    try {
                        msgFromOpponent = bufferedReader.readLine();
                        System.out.println(msgFromOpponent);
                        listener.haveTwoPlayers(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        // metodo a ser chamado ao clicar no botao de confirmar escolha de valor
        public void sendAim(String aim) {
            try {
                bufferedWriter.write(aim);
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // metodo a ser chamado ao realizar uma tentativa
        public void sendAttempt(String attempt) {
            try {
                bufferedWriter.write(attempt);
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.tiromosca.network.connection.client;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;

@Data
public class PlayerClient {

    private PlayerClientConnection playerClientConnection;
    private int opponentID;
    private int playerID;
    private int numOfVictories;
    private int numOfOpponentVictories;

    private String playerAim;
    private String opponentAttempt;
    private String opponentAim;
    private String lastAttempt;
    private String shots;
    private String flies;

    private String userName;

    private Boolean firstPlayerToPlay;
    private Boolean itsMyTimeToPlay;
    private Boolean isActualChampion;

    private ConnectionListener connectionListener;
    private MatchListener matchListener;

    public void sendAim(String aim) {
        playerClientConnection.sendAim(aim);
    }

    public void sendAttempt(String sendAttempt) {
        playerClientConnection.sendAttempt(sendAttempt);
    }

    public void connectToServer() {
        playerClientConnection = new PlayerClientConnection();
        playerID = playerClientConnection.getPlayerID();
    }

    public void sendUserName(String username) {
        playerClientConnection.sendUserName(username);
    }

    public void verifyOpponentConnection() {
        playerClientConnection.verifyOpponentConnection();
    }

    public void getMatchResult() {
        playerClientConnection.readMatchResult();
    }

    public String getOpponentAim() {
        return playerClientConnection.getOpponentAim();
    }

    public String getOpponentUserName() {
        return playerClientConnection.getOpponentUserName();
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
                numOfVictories = 0;
                numOfOpponentVictories = 0;
                this.socket = new Socket("127.0.0.1", 20525);
                var outputStream = new OutputStreamWriter(socket.getOutputStream());
                var inputStream = new InputStreamReader(socket.getInputStream());
                this.bufferedWriter = new BufferedWriter(outputStream);
                this.bufferedReader = new BufferedReader(inputStream);
                this.playerID = bufferedReader.read();
                System.out.println("Player " + playerID + " conectado");
                this.opponentID = (playerID == 1) ? 2 : 1;
                firstPlayerToPlay = playerID == 1;
                itsMyTimeToPlay = playerID == 1;
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
                        System.out.println("IMPRIMI A MSG QUE RECEBI DO OPONENTE ENQUANTO ESTAVA verifyOpponentConnection");
                        connectionListener.itsTimeToPlay();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        public void readMatchResult() {
            new Thread(() -> {
                String flies = StringUtils.EMPTY;
                String shots = StringUtils.EMPTY;
                int winnerValue = 0;
                int looserValue = 0;

                while (flies.isEmpty() || shots.isEmpty()) {
                    try {
                        if (!itsMyTimeToPlay) {
                            opponentAttempt = bufferedReader.readLine();
                        }
                        flies = bufferedReader.readLine();
                        System.out.println(flies);
                        shots = bufferedReader.readLine();
                        System.out.println(shots);
                        winnerValue = bufferedReader.read();
                        looserValue = bufferedReader.read();
                        matchListener.haveAResult(flies, shots, winnerValue, looserValue);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        // metodo para enviar o username ao servidor
        public void sendUserName(String username) {
            try {
                bufferedWriter.write(username);
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // metodo a ser chamado ao clicar no botao de confirmar escolha de valor
        public void sendAim(String aim) {
            try {
                System.out.println("Printando escolha atual!!!");
                System.out.println(aim);
                playerAim = aim;
                bufferedWriter.write(aim);
                bufferedWriter.flush();
                System.out.println("ENVIEI!!!");
                System.out.println(aim);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // metodo a ser chamado ao realizar uma tentativa
        public void sendAttempt(String attempt) {
            try {
                lastAttempt = attempt;
                bufferedWriter.write(attempt);
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getOpponentAim() {
            var opponentValue = StringUtils.EMPTY;
            try {
                opponentValue = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return opponentValue;
        }

        public String getOpponentUserName() {
            var opponentUserName = StringUtils.EMPTY;
            try {
                opponentUserName = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return opponentUserName;
        }
    }
}

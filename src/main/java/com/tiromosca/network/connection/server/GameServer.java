package com.tiromosca.network.connection.server;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Setter
@Getter
public class GameServer {

    // ideia para criar mais de uma sala de jogos:
    // Ter um array de Game server (talvez??)

    private ServerSocket serverSocket;
    private int numOfPlayers;
    private int victoriesPlayer1;
    private int victoriesPlayer2;

    private GameServerConnection player1;
    private GameServerConnection player2;

    public GameServer() {
        victoriesPlayer1 = 0;
        victoriesPlayer2 = 0;
        numOfPlayers = 0;

        // instanciando o sv socket na porta 20525
        try {
            serverSocket = new ServerSocket(20525);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Esperando por conexoes...");
            while (numOfPlayers < 2) {
                Socket socket = serverSocket.accept();
                numOfPlayers++;
                System.out.println("Player" + numOfPlayers + " conectado");
                GameServerConnection gameServerConnection = new GameServerConnection(socket, numOfPlayers);
                if (numOfPlayers == 1) {
                    player1 = gameServerConnection;
                } else {
                    player2 = gameServerConnection;
                }
                Thread thread = new Thread(gameServerConnection);
                thread.start();
            }

            System.out.println("Agora temos 2 players conectados");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class GameServerConnection implements Runnable {

        private Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private int playerID;
        private String playerOneAttempt;
        private String playerTwoAttempt;
        private String playerOneAim;
        private String playerTwoAim;
        private int fliesPlayer1;
        private int fliesPlayer2;

        public GameServerConnection(Socket socket, int playerID) {
            this.socket = socket;
            this.playerID = playerID;
            try {
                var outputStream = new OutputStreamWriter(socket.getOutputStream());
                var inputStream = new InputStreamReader(socket.getInputStream());
                this.bufferedWriter = new BufferedWriter(outputStream);
                this.bufferedReader = new BufferedReader(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {

                bufferedWriter.write(playerID);
                bufferedWriter.flush();

                if (playerID == 2) {
                    player1.bufferedWriter.write("Ready!\n");
                    player1.bufferedWriter.flush();
                }

//                if (playerID == 1) {
//                    playerOneAim = bufferedReader.readLine();
//                } else {
//                    playerTwoAim = bufferedReader.readLine();
//                }

                while (true) {

//                    if (playerID == 1) {
//                        playerOneAttempt = bufferedReader.readLine();
//                        List<Integer> shotsAndFies = GameMatchManager.getResultOfAMatch(playerOneAttempt, playerTwoAim);
//                        fliesPlayer1 = shotsAndFies.get(1);
//                        List<String> result = GameMatchManager.formatResultOfAMatch(shotsAndFies.get(0), fliesPlayer1);
//                        // se shotsAndFies.get(1) == 3, signfica que esse player acertou o numero, porem, ainda eh necessario
//                        // verificar se o outro player tambem acertou todos na vez dele
//
//                    } else {
//                        playerTwoAttempt = bufferedReader.readLine();
//                        List<Integer> shotsAndFies = GameMatchManager.getResultOfAMatch(playerTwoAttempt, playerOneAim);
//                        fliesPlayer2 = shotsAndFies.get(1);
//                        List<String> result = GameMatchManager.formatResultOfAMatch(shotsAndFies.get(0), fliesPlayer2);
//                    }
//
//                    if (fliesPlayer1 == 3 || fliesPlayer2 == 3) {
//                        if (fliesPlayer1 == 3 && fliesPlayer2 == 3) {
//                            // Exibir tela de empate
//
//                        } if (fliesPlayer1 == 3) {
//                            victoriesPlayer1++;
//                            // Exibir tela de vitoria para o player 1 e tela de derrota para o player 2
//
//                        } else {
//                            victoriesPlayer2++;
//                            // Exibir tela de vitoria para p player 2
//                        }
//                    } else {
//                        // continuar
//                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.acceptConnections();
    }
}

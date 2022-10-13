package com.tiromosca.network.connection.server;

import com.tiromosca.network.game.util.GameMatchManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

@Setter
@Getter
public class GameServer {

    private ServerSocket serverSocket;
    private int numOfPlayers;
    private int victoriesPlayer1;
    private int victoriesPlayer2;

    private int round;

    private String playerOneAttempt;
    private String playerTwoAttempt;

    private String playerOneAim;
    private String playerTwoAim;

    private int fliesPlayer1;
    private int fliesPlayer2;

    private GameServerConnection player1;
    private GameServerConnection player2;

    public GameServer() {
        victoriesPlayer1 = 0;
        victoriesPlayer2 = 0;
        numOfPlayers = 0;
        round = 1;

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

                if (playerID == 1) {
                    playerOneAim = bufferedReader.readLine();
                } else {
                    playerTwoAim = bufferedReader.readLine();
                    player1.bufferedWriter.write("Ready!\n");
                    player1.bufferedWriter.flush();
                }

                while (victoriesPlayer1 == 0 && victoriesPlayer2 == 0) {

                    if (round % 2 != 0) {
                        System.out.println("ENTREI NO ROUND 1");
                        playerOneAttempt = bufferedReader.readLine();
                        List<Integer> shotsAndFies = GameMatchManager.getResultOfAMatch(playerOneAttempt, playerTwoAim);
                        fliesPlayer1 = shotsAndFies.get(1);
                        List<String> result = GameMatchManager.formatResultOfAMatch(shotsAndFies.get(0), fliesPlayer1);

                        System.out.println("Enviando as respostas para o player 1");

                        //Envia o resultado ao player 1
                        player1.bufferedWriter.write(result.get(1)+'\n');
                        player1.bufferedWriter.flush();
                        player1.bufferedWriter.write(result.get(0)+'\n');
                        player1.bufferedWriter.flush();
                        if (fliesPlayer1 == 3) {
                            victoriesPlayer1++;
                            // winner
                            player1.bufferedWriter.write(1);
                            player1.bufferedWriter.flush();
                            //  looser
                            player1.bufferedWriter.write(0);
                            player1.bufferedWriter.flush();
                        } else {
                            // winner
                            player1.bufferedWriter.write(0);
                            player1.bufferedWriter.flush();
                            //  looser
                            player1.bufferedWriter.write(0);
                            player1.bufferedWriter.flush();
                        }

                        System.out.println("Enviando as respostas para o player 2");
                        //Envia o resultado ao player 2
                        player2.bufferedWriter.write(playerOneAttempt +'\n');
                        player2.bufferedWriter.flush();
                        player2.bufferedWriter.write(result.get(1)+'\n');
                        player2.bufferedWriter.flush();
                        player2.bufferedWriter.write(result.get(0)+'\n');
                        player2.bufferedWriter.flush();
                        if (fliesPlayer1 == 3) {
                            // winner
                            player2.bufferedWriter.write(0);
                            player2.bufferedWriter.flush();
                            // looser
                            player2.bufferedWriter.write(1);
                            player2.bufferedWriter.flush();
                        } else {
                            // winner
                            player2.bufferedWriter.write(0);
                            player2.bufferedWriter.flush();
                            // looser
                            player2.bufferedWriter.write(0);
                            player2.bufferedWriter.flush();
                        }
                    } else {
                        System.out.println("ENTREI ROUND 2");
                        playerTwoAttempt = bufferedReader.readLine();
                        System.out.println("TESTE 1!!!!!!!!!!");
                        List<Integer> shotsAndFies = GameMatchManager.getResultOfAMatch(playerTwoAttempt, playerOneAim);
                        fliesPlayer2 = shotsAndFies.get(1);
                        List<String> result = GameMatchManager.formatResultOfAMatch(shotsAndFies.get(0), fliesPlayer2);

                        System.out.println("TESTE 2!!!!!!!!!!");
                        System.out.println("Enviando as respostas para o player 2");

                        player2.bufferedWriter.flush();

                        //Envia o resultado ao player 1
                        player2.bufferedWriter.write(result.get(1)+'\n');
                        player2.bufferedWriter.flush();
                        player2.bufferedWriter.write(result.get(0)+'\n');
                        player2.bufferedWriter.flush();
                        if (fliesPlayer1 == 3) {
                            victoriesPlayer2++;
                            // winner
                            player2.bufferedWriter.write(1);
                            player2.bufferedWriter.flush();
                            //  looser
                            player2.bufferedWriter.write(0);
                            player2.bufferedWriter.flush();
                        } else {
                            // winner
                            player2.bufferedWriter.write(0);
                            player1.bufferedWriter.flush();
                            //  looser
                            player2.bufferedWriter.write(0);
                            player2.bufferedWriter.flush();
                        }

                        System.out.println("Enviando as respostas para o player 1");
                        //Envia o resultado ao player 2
                        player1.bufferedWriter.write(playerTwoAttempt +'\n');
                        player1.bufferedWriter.flush();
                        player1.bufferedWriter.write(result.get(1)+'\n');
                        player1.bufferedWriter.flush();
                        player1.bufferedWriter.write(result.get(0)+'\n');
                        player1.bufferedWriter.flush();
                        if (fliesPlayer1 == 3) {
                            // winner
                            player1.bufferedWriter.write(0);
                            player1.bufferedWriter.flush();
                            // looser
                            player1.bufferedWriter.write(1);
                            player1.bufferedWriter.flush();
                        } else {
                            // winner
                            player1.bufferedWriter.write(0);
                            player1.bufferedWriter.flush();
                            // looser
                            player1.bufferedWriter.write(0);
                            player1.bufferedWriter.flush();
                        }
                    }
                    round++;
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

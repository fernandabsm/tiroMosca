package com.tiromosca.network.connection.server;

import com.tiromosca.network.game.util.GameMatchManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.InetAddress;
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

    private int lastWinner;

    private int round;

    private String playerOneAttempt;
    private String playerTwoAttempt;

    private String playerOneAim;
    private String playerTwoAim;

    private String userNamePlayer1;
    private String userNamePlayer2;

    private int fliesPlayer1;
    private int fliesPlayer2;

    private boolean flag;

    private boolean playerOneTime;
    private boolean playerTwoTime;

    private GameServerConnection player1;
    private GameServerConnection player2;

    public GameServer() {
        userNamePlayer1 = StringUtils.EMPTY;
        userNamePlayer2 = StringUtils.EMPTY;
        lastWinner = 0;
        playerOneTime = true;
        playerTwoTime = false;
        flag = true;

        victoriesPlayer1 = 0;
        victoriesPlayer2 = 0;

        numOfPlayers = 0;

        // instanciando o sv socket na porta 20525
        try {
            InetAddress address = InetAddress.getByName("127.0.0.1");
            serverSocket = new ServerSocket(20525, 50, address);
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

                // Envia para cada usuario seu ID -> 1 ou 2
                bufferedWriter.write(playerID);
                bufferedWriter.flush();

                // Recebe os nomes escolhidos pelos usuarios
                if (playerID == 1) {
                    userNamePlayer1 = player1.bufferedReader.readLine();
                } else {
                    userNamePlayer2 = player2.bufferedReader.readLine();
                }

                if (playerID == 2) {
                    player1.bufferedWriter.write("Ready!\n");
                    player1.bufferedWriter.flush();
                }

                while (true) {
                    flag = true;
                    System.out.println("Last winner" + lastWinner);
                    if (lastWinner == 0 || lastWinner == 1) {
                        playerOneTime = true;
                        playerTwoTime = false;
                        if (playerID == 1) {
                            playerOneAim = player1.bufferedReader.readLine();
                        } else {
                            playerTwoAim = player2.bufferedReader.readLine();
                            player1.bufferedWriter.write("Ready!\n");
                            player1.bufferedWriter.flush();
                        }
                    } else {
                        System.out.println("Cheguei");
                        playerOneTime = false;
                        playerTwoTime = true;
                        if (playerID == 2) {
                            System.out.println("Cheguei aqui");
                            playerTwoAim = player2.bufferedReader.readLine();
                        } else {
                            System.out.println("Aqui tambem");
                            playerOneAim = player1.bufferedReader.readLine();
                            System.out.println("Vou avisar ao player 2 que o player 1 escolheu tambem");
                            player2.bufferedWriter.write("Ready!\n");
                            System.out.println("Avisei");
                            player2.bufferedWriter.flush();
                        }

                    }

                    while (flag) {
                        if (playerOneTime && playerID == 1) {
                            playerOneAttempt = player1.bufferedReader.readLine();
                            List<Integer> shotsAndFies = GameMatchManager.getResultOfAMatch(playerOneAttempt, playerTwoAim);
                            fliesPlayer1 = shotsAndFies.get(1);
                            List<String> result = GameMatchManager.formatResultOfAMatch(shotsAndFies.get(0), fliesPlayer1);

                            //Envia o resultado ao player 1
                            player1.bufferedWriter.write(result.get(1) + '\n');
                            player1.bufferedWriter.flush();
                            player1.bufferedWriter.write(result.get(0) + '\n');
                            player1.bufferedWriter.flush();
                            if (fliesPlayer1 == 3) {
                                flag = false;
                                victoriesPlayer1++;
                                lastWinner = 1;

                                // winner
                                player1.bufferedWriter.write(1);
                                player1.bufferedWriter.flush();

                                //  looser
                                player1.bufferedWriter.write(0);
                                player1.bufferedWriter.flush();

                                player1.bufferedWriter.write(playerTwoAim + '\n');
                                player1.bufferedWriter.flush();

                                player1.bufferedWriter.write(userNamePlayer2 + '\n');
                                player1.bufferedWriter.flush();

                                playerTwoTime = false;
                                playerOneTime = true;
                            } else {
                                // winner
                                player1.bufferedWriter.write(0);
                                player1.bufferedWriter.flush();

                                //  looser
                                player1.bufferedWriter.write(0);
                                player1.bufferedWriter.flush();

                                playerTwoTime = true;
                                playerOneTime = false;
                            }

                            //Envia o resultado ao player 2
                            player2.bufferedWriter.write(playerOneAttempt + '\n');
                            player2.bufferedWriter.flush();
                            player2.bufferedWriter.write(result.get(1) + '\n');
                            player2.bufferedWriter.flush();
                            player2.bufferedWriter.write(result.get(0) + '\n');
                            player2.bufferedWriter.flush();
                            if (fliesPlayer1 == 3) {
                                flag = false;
                                lastWinner = 1;

                                // winner
                                player2.bufferedWriter.write(0);
                                player2.bufferedWriter.flush();

                                // looser
                                player2.bufferedWriter.write(1);
                                player2.bufferedWriter.flush();

                                player2.bufferedWriter.write(playerOneAim + '\n');
                                player2.bufferedWriter.flush();

                                player2.bufferedWriter.write(userNamePlayer1 + '\n');
                                player2.bufferedWriter.flush();

                                playerTwoTime = false;
                                playerOneTime = true;
                            } else {
                                // winner
                                player2.bufferedWriter.write(0);
                                player2.bufferedWriter.flush();
                                // looser
                                player2.bufferedWriter.write(0);
                                player2.bufferedWriter.flush();

                                playerTwoTime = true;
                                playerOneTime = false;
                            }
                        } else if (playerTwoTime && playerID == 2) {
                            playerTwoAttempt = player2.bufferedReader.readLine();
                            List<Integer> shotsAndFies = GameMatchManager.getResultOfAMatch(playerTwoAttempt, playerOneAim);
                            fliesPlayer2 = shotsAndFies.get(1);
                            List<String> result = GameMatchManager.formatResultOfAMatch(shotsAndFies.get(0), fliesPlayer2);

                            //Envia o resultado ao player 2
                            player2.bufferedWriter.write(result.get(1) + '\n');
                            player2.bufferedWriter.flush();
                            player2.bufferedWriter.write(result.get(0) + '\n');
                            player2.bufferedWriter.flush();
                            if (fliesPlayer2 == 3) {
                                flag = false;
                                lastWinner = 2;
                                victoriesPlayer2++;

                                // winner
                                player2.bufferedWriter.write(1);
                                player2.bufferedWriter.flush();

                                //  looser
                                player2.bufferedWriter.write(0);
                                player2.bufferedWriter.flush();

                                player2.bufferedWriter.write(playerOneAim + '\n');
                                player2.bufferedWriter.flush();

                                player2.bufferedWriter.write(userNamePlayer1 + '\n');
                                player2.bufferedWriter.flush();

                                playerTwoTime = true;
                                playerOneTime = false;
                            } else {
                                // winner
                                player2.bufferedWriter.write(0);
                                player2.bufferedWriter.flush();
                                //  looser
                                player2.bufferedWriter.write(0);
                                player2.bufferedWriter.flush();

                                playerTwoTime = false;
                                playerOneTime = true;
                            }

                            //Envia o resultado ao player 1
                            player1.bufferedWriter.write(playerTwoAttempt + '\n');
                            player1.bufferedWriter.flush();
                            player1.bufferedWriter.write(result.get(1) + '\n');
                            player1.bufferedWriter.flush();
                            player1.bufferedWriter.write(result.get(0) + '\n');
                            player1.bufferedWriter.flush();
                            if (fliesPlayer2 == 3) {
                                flag = false;
                                lastWinner = 2;
                                // winner
                                player1.bufferedWriter.write(0);
                                player1.bufferedWriter.flush();
                                // looser
                                player1.bufferedWriter.write(1);
                                player1.bufferedWriter.flush();

                                player1.bufferedWriter.write(playerTwoAim + '\n');
                                player1.bufferedWriter.flush();

                                player1.bufferedWriter.write(userNamePlayer2 + '\n');
                                player1.bufferedWriter.flush();

                                playerTwoTime = true;
                                playerOneTime = false;
                            } else {
                                // winner
                                player1.bufferedWriter.write(0);
                                player1.bufferedWriter.flush();
                                // looser
                                player1.bufferedWriter.write(0);
                                player1.bufferedWriter.flush();

                                playerTwoTime = false;
                                playerOneTime = true;
                            }
                        }
                    }
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

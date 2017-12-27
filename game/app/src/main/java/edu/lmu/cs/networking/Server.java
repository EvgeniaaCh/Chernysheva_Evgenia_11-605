package edu.lmu.cs.networking;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static volatile List<Game> gamesArchive = new ArrayList<Game>() {
    };

    /**
     * Runs the application. Pairs up clients that connect.
     */
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8102);
        System.out.println("Server is Running");
        try {
            while (true) {

                Panel panel = new Panel();
                Game game = new Game();
                Object[] map = panel.generator(game);
                gamesArchive.add(game);
                Game.Player playerX = game.new Player(listener.accept(), 'X');
                Game.Player playerO = game.new Player(listener.accept(), 'O');
                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                game.thisPlayer = playerX;
                game.setPanel(map, panel.getLocationX(), panel.getLocationO());
                playerX.start();
                playerO.start();
                panel.printMap(map);
            }
        } finally {
            listener.close();
        }
    }
}


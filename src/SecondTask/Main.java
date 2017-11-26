package SecondTask;

import java.io.IOException;

/**
 * Created by Dmitry on 10.06.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
        Client client = new Client();

        server.startGame();
        client.playGame();

    }
}

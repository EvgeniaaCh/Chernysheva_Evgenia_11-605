package SecondTask;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Dmitry on 10.06.2017.
 */
public class Client implements Runnable {

    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;

    public Client(){
        try {
            socket = new Socket("127.0.0.1", 3232);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void playGame() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String playerAnswer = scanner.nextLine();
        outputStream.writeUTF(playerAnswer);
        while (!playerAnswer.equals("Сдаюсь.")){
            String serverAnswer = inputStream.readUTF();
            System.out.println("Cервер ответил: "+serverAnswer);
            playerAnswer = scanner.nextLine();
            outputStream.writeUTF(playerAnswer);
        }
    }

    @Override
    public void run() {
        try {
            playGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package SecondTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Dmitry on 10.06.2017.
 */
public class Server {

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ServerSocket serverSocket;
    private ArrayList<String> alreadyUsed = new ArrayList<>();
    private String[] cities = new String[]{"Kazan", "Moskow", "Piter", "Chelny", "London", "Berlin", "Astrakhan", "Ufa"};

    public Server() throws IOException {
        serverSocket = new ServerSocket(3232);
    }

    public void startServer() throws IOException {
        Socket client = serverSocket.accept();
        dataInputStream = new DataInputStream(client.getInputStream());
        dataOutputStream = new DataOutputStream(client.getOutputStream());
    }

    public void startGame() throws IOException {
        String serverAnswer = "";
        while(!serverAnswer.equals("You won")) {
            String userAnswer = dataInputStream.readUTF();
            if (!alreadyUsed.contains(userAnswer)) {
                if(serverAnswer.equals("")){
                    alreadyUsed.add(userAnswer);
                    serverAnswer = findCity(userAnswer);
                    if (!serverAnswer.equals("You won")) {
                        dataOutputStream.writeUTF(serverAnswer);
                    }else serverSocket.close();
                }else
                if (userAnswer.charAt(0) == serverAnswer.charAt(serverAnswer.length())) {
                    alreadyUsed.add(userAnswer);
                    serverAnswer = findCity(userAnswer);
                    if (!serverAnswer.equals("You won")) {
                        dataOutputStream.writeUTF(serverAnswer);
                    }else serverSocket.close();
                }else{
                    dataOutputStream.writeUTF("You loos");
                    serverSocket.close();
                }
            }else{
                dataOutputStream.writeUTF("You loos");
                serverSocket.close();
            }
        }
    }

    private String findCity(String userAnswer){
        for(String city:cities){
            if(!alreadyUsed.contains(city)){
                if(city.charAt(0)==userAnswer.charAt(city.length())){
                    return city;
                }
            }
        }
        return "You won";
    }
}

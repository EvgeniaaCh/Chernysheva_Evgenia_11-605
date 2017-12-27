package edu.lmu.cs.networking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class Client {

    private JFrame frame = new JFrame("Minotaur");
    private JLabel messageLabel = new JLabel("");
    private ImageIcon icon;
    private ImageIcon opponentIcon;
    private ImageIcon white = createImageIcon("smallwhite.png", "White image");
    private ImageIcon barrier = createImageIcon("smallbarrier.png", "Barrier image");
    private ImageIcon granite = createImageIcon("smallgranite.png", "Granite image");
    private ImageIcon destroyed = createImageIcon("smalldestroyed.png", "Destroyed image");

    private ImageIcon destroyedIcon;
    private ImageIcon destroyedOpponentIcon;

    private final int SIZE = 81;
    private final int columnLength = (int) Math.sqrt(SIZE);
    private Square[] panel = new Square[SIZE];
    private Square[] opponentPanel = new Square[SIZE];
    private int currentLocation;
    private int opponentCurrentLocation;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private boolean error = false;

    private boolean wasChange = false;

    private Client(String serverAddress) throws Exception {
        int PORT = 8102;
        try {
            socket = new Socket(serverAddress, PORT);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.out.println("CLIENT");
                    out.println("EXIT");
                }
            });

            messageLabel.setBackground(Color.lightGray);
            frame.getContentPane().add(messageLabel, "South");

            frame.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if ((key == KeyEvent.VK_LEFT)) {
                        out.println("MOVE LEFT");
                    }
                    if ((key == KeyEvent.VK_RIGHT)) {
                        out.println("MOVE RIGHT");
                    }
                    if ((key == KeyEvent.VK_UP)) {
                        out.println("MOVE UP");
                    }
                    if ((key == KeyEvent.VK_DOWN)) {
                        out.println("MOVE DOWN");
                    }
                }
            });

            frame.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        if (!wasChange) {
                            out.println("CHANGE");
                            wasChange = true;
                            messageLabel.setText("ВЫБЕРИТЕ НАПРАВЛЕНИЕ ДВИЖЕНИЯ");
                        }
                    }
                }
            });



          /*  JPanel labelPanel0 = new JPanel();
            labelPanel0.setLayout(new BoxLayout(labelPanel0, BoxLayout.X_AXIS));
            JLabel label4 = new JLabel("Вы");
            JLabel label5 = new JLabel("Противник");
            label4.setAlignmentX(Component.LEFT_ALIGNMENT);
            label5.setAlignmentX(Component.RIGHT_ALIGNMENT);
            labelPanel0.add(label4);
            labelPanel0.add(label5);
            */

            JPanel boardPanel = new JPanel();
            JPanel opponentBoardPanel = new JPanel();
            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));


            JLabel label0 = new JLabel("Правила игры:");
            JLabel label = new JLabel("Бросок гранаты: ЛКМ");
            JLabel label2 = new JLabel("Завершение хода: ПКМ");
            JLabel label3 = new JLabel("Передвижение: стрелки ");


            label0.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label2.setAlignmentX(Component.CENTER_ALIGNMENT);
            label3.setAlignmentX(Component.CENTER_ALIGNMENT);

            labelPanel.add(label0);
            labelPanel.add(label);
            labelPanel.add(label2);
            labelPanel.add(label3);


            boardPanel.setLayout(new GridLayout(columnLength, columnLength, columnLength - 1, columnLength - 1));
            opponentBoardPanel.setLayout(new GridLayout(columnLength, columnLength, columnLength - 1, columnLength - 1));

            for (int i = 0; i < panel.length; i++) {
                final int j = i;
                panel[i] = new Square();
                panel[i].setIcon(white);
                panel[i].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (j == currentLocation - 1)
                                out.println("BOMB LEFT");
                            else if (j == currentLocation + 1)
                                out.println("BOMB RIGHT");
                            else if (j == currentLocation - columnLength)
                                out.println("BOMB UP");
                            else if (j == currentLocation + columnLength)
                                out.println("BOMB DOWN");
                        }
                    }
                });
                boardPanel.add(panel[i]);
            }
            frame.getContentPane().add(boardPanel, "West");

            frame.getContentPane().add(labelPanel, "Center");

            for (int i = 0; i < opponentPanel.length; i++) {
                opponentPanel[i] = new Square();
                opponentPanel[i].setIcon(white);
                opponentBoardPanel.add(opponentPanel[i]);
            }
            frame.getContentPane().add(opponentBoardPanel, "East");

        } catch (ConnectException e) {
            errorMessage();
        }
    }

    public void errorMessage() {
        JOptionPane.showConfirmDialog(frame,
                "Sorry, server is not running",
                "Error",
                JOptionPane.CLOSED_OPTION);
        error = true;
    }

    public boolean error() {
        return error;
    }


    private void play() throws Exception {
        String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME")) {
                char mark = response.charAt(8);
                icon = mark == 'X' ? createImageIcon("small_X.png", "X image") : createImageIcon("small_O.png", "O image");
                opponentIcon = mark == 'X' ? createImageIcon("small_O.png", "X image") : createImageIcon("small_X.png", "O image");

                destroyedIcon = mark == 'X' ? createImageIcon("small_destroyedX.png", "destroyedX image") : createImageIcon("small_destroyedO.png", "destroyedO image");
                destroyedOpponentIcon = mark == 'X' ? createImageIcon("small_destroyedO.png", "destroyedX image") : createImageIcon("small_destroyedX.png", "destroyedO image");
                frame.setTitle("Labirynth Of Minotaur - Player " + mark);
            }
            while (true) {
                response = in.readLine();

                if (response.startsWith("YOUR_MOVE")) {
                    wasChange = false;
                    messageLabel.setText("ВАШ ХОД");
                }

                else if (response.startsWith("START")) {
                    startLocation(panel, icon);

                } else if (response.startsWith("OPPONENT_START")) {
                    startLocation(opponentPanel, opponentIcon);

                } else if (response.startsWith("OPEN")) {
                    openLocation(response, panel, 5, true);

                } else if (response.startsWith("OPPONENT_OPEN")) {
                    openLocation(response, opponentPanel, 14, false);

                } else if (response.startsWith("VALID_MOVE")) {
                    moveLocation(response, panel, 11, icon, true);

                } else if (response.startsWith("OPPONENT_MOVED")) {
                    moveLocation(response, opponentPanel, 15, opponentIcon, false);

                } else if (response.startsWith("DESTROYED")) {
                    destroyedLocation(response, panel, 10, true);

                } else if (response.startsWith("OPPONENT_DESTROYED")) {
                    destroyedLocation(response, opponentPanel, 19, false);

                } else if (response.startsWith("NOT_DESTROYED")) {
                    notDestroyedLocation(response, panel, 14, true);

                } else if (response.startsWith("OPPONENT_NOT_DESTROYED")) {
                    notDestroyedLocation(response, opponentPanel, 23, false);

                } else if (response.startsWith("THROW_INTO_THE_VOID")) {
                    throwIntoTheVoid(response, panel, 20, icon, true);

                } else if (response.startsWith("OPPONENT_THROW_INTO_THE_VOID")) {
                    throwIntoTheVoid(response, opponentPanel, 29, opponentIcon, false);


                } else if (response.startsWith("VICTORY")) {
                    messageLabel.setText("ВЫ ПОБЕДИЛИ!");
                    break;

                } else if (response.startsWith("DEFEAT")) {
                    messageLabel.setText("ВЫ ПРОИГРАЛИ");
                    break;

                } else if (response.startsWith("MESSAGE")) {
                    messageLabel.setText(response.substring(8));
                }
            }
            out.println("QUIT");
        } catch (SocketException e) {
            socket.close();
            errorMessage();
            System.exit(0);
        } finally {
            try {
                socket.close();
            }
            catch (NullPointerException e) {
            }
        }
    }

    private int direction(String subCommand, boolean current) { //направление хода
        int location;
        if (current)
            location = currentLocation;
        else
            location = opponentCurrentLocation;

        if (subCommand.startsWith("LEFT"))
            return location - 1;
        else if (subCommand.startsWith("RIGHT"))
            return location + 1;
        else if (subCommand.startsWith("UP"))
            return location - columnLength;
        else if (subCommand.startsWith("DOWN"))
            return location + columnLength;
        else
            return -1;
    }

    private void startLocation(Square[] board, ImageIcon icon) {
        int startLocation = (SIZE - 1) / 2;
        if (icon.equals(this.icon)) {
            currentLocation = startLocation;
        } else {
            opponentCurrentLocation = startLocation;
        }
        board[startLocation].setIcon(icon);
        board[startLocation].repaint();
    }

    private void openLocation(String response, Square[] panel, int n, boolean current) {
        int code = Integer.parseInt(response.substring(n, n + 1));
        String turn = response.substring(n + 2);
        int playerLocation;

        if (current)
            playerLocation = currentLocation;
        else
            playerLocation = opponentCurrentLocation;

        if (playerLocation % columnLength == 0 && turn.equals("LEFT")) {

        } else if ((playerLocation + 1) % columnLength == 0 && turn.equals("RIGHT")) {

        } else if (playerLocation <= columnLength && turn.equals("UP")) {

        } else if (SIZE - playerLocation <= columnLength && turn.equals("DOWN")) {

        } else {

            int location = direction(turn, current); //определяем координату по направлению

            if (code == 0) {
                panel[location].setIcon(white);
            }
            if (code == 1 && panel[location].getIcon() != granite) {
                panel[location].setIcon(barrier);
            }
            panel[location].repaint();

            if (current)
                messageLabel.setText("ОТКРЫТО:  " + turn);
            else
                messageLabel.setText("ПРОТИВНИК УЗНАЛ, ГДЕ ОТКРЫТО:  " + turn);
        }
    }

    private void moveLocation(String response, Square[] panel, int n, ImageIcon icon, boolean current) {
        String turn = response.substring(n);
        int location = direction(turn, current); //определяем координату по направлению
        if (response.startsWith("VALID_MOVE_") || response.startsWith("OPPONENT_MOVED_")) { //если команда с обломками
            if (icon.equals(this.icon)) { //игрок на руинах
                panel[location].setIcon(destroyedIcon);
            } else {
                panel[location].setIcon(destroyedOpponentIcon);
            }
        }
        else {
            panel[location].setIcon(icon);
        }

        panel[location].repaint();

        if (icon.equals(this.icon)) { //старая локация игрока
            if (panel[currentLocation].getIcon().equals(destroyedIcon)) {
                panel[currentLocation].setIcon(destroyed);
            }
            else {
                panel[currentLocation].setIcon(white);
            }
            panel[currentLocation].repaint();
            currentLocation = location;

        } else {
            if (panel[opponentCurrentLocation].getIcon().equals((destroyedOpponentIcon))) {
                panel[opponentCurrentLocation].setIcon(destroyed);
            }
            else {
                panel[opponentCurrentLocation].setIcon(white);
            }
            panel[opponentCurrentLocation].repaint();
            opponentCurrentLocation = location;
        }
    }

    private void destroyedLocation(String response, Square[] board, int n, boolean current) {
        String turn = response.substring(n);
        int location = direction(turn, current); //определяем координату по направлению
        board[location].setIcon(destroyed);
        board[location].repaint();

        if (current)
            messageLabel.setText("СТЕНА УНИЧТОЖЕНА:  " + turn);
        else
            messageLabel.setText("ПРОТИВНИК НЕ СМОГ УНИЧТОЖИТЬ СТЕНУ:  " + turn);
    }

    private void notDestroyedLocation(String response, Square[] board, int n, boolean current) {
        String turn = response.substring(n);
        int location = direction(turn, current); //определяем координату по направлению
        board[location].setIcon(granite);
        board[location].repaint();

        if (current)
            messageLabel.setText("СТЕНА НЕ УНИЧТОЖЕНА:  " + turn);
        else
            messageLabel.setText("ПРОТИВНИК НЕ СМОГ УНИЧТОЖИТЬ СТЕНУ:  " + turn);

    }

    private void throwIntoTheVoid(String response, Square[] board, int n, ImageIcon icon, boolean current) {
        String turn = response.substring(n);
        if (response.startsWith("THROW_INTO_THE_VOID_") || response.startsWith("OPPONENT_THROW_INTO_THE_VOID_")) {
            int location = direction(turn, current); //определяем координату по направлению
            if (icon.equals(this.icon)) {
                board[location].setIcon(destroyed);
            }
            else {
                board[location].setIcon(destroyed);
            }
            board[location].repaint();
        }
        else {

            if (current)
                messageLabel.setText("БРОСОК В ПУСТОТУ:  " + turn);
            else
                messageLabel.setText("ПРОТИВНИК СОВЕРШИЛ БРОСОК В ПУСТОТУ:  " + turn);
        }
    }

    private boolean wantsToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(frame,
                "Want to play again?",
                "Play again?",
                JOptionPane.YES_NO_OPTION);
        frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }


    static class Square extends JPanel {
        JLabel label = new JLabel((Icon) null);

        Square() {
            setBackground(Color.white);
            add(label);
        }

        void setIcon(Icon icon) {
            label.setIcon(icon);
        }

        Icon getIcon() {
            return label.getIcon();
        }
    }


    public static void main(String[] args) throws Exception {
        String serverAddress = null;
        boolean askAddress = true;
        while (true) {
            if (askAddress) {
                IP ip = new IP();
                serverAddress = ip.setIP();
            }
            Client client = new Client(serverAddress);
            if (!client.error()) {
                client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                client.frame.setSize(860, 350);
                client.frame.setLocation(500, 500);
                client.frame.setVisible(true);
                client.frame.setResizable(false);
                client.play();

                if (!client.wantsToPlayAgain()) {
                    break;
                }
                else {
                    askAddress = false;
                }
            } else {
                System.exit(0);
            }
        }
    }

    private static class IP {

        String setIP() {
            int response = JOptionPane.showConfirmDialog(null,
                    "It's your server?",
                    "Input IP",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                return "localhost";
            }
            else if (response == JOptionPane.NO_OPTION) {
                return JOptionPane.showInputDialog(null, "Input IP:", "Input", JOptionPane.QUESTION_MESSAGE);
            }
            else {
                System.exit(0);
            }
            return null;
        }
    }


    private ImageIcon createImageIcon(String path,
                                      String description) {

        java.net.URL imgURL = getClass().getClassLoader().getResource(path);
        System.out.println(imgURL);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);

        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
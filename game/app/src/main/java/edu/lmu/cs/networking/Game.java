package edu.lmu.cs.networking;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PrintWriter;
        import java.net.Socket;

public class Game {
    private Object[] panel;
    private int columnLength = (int) Math.sqrt(25);

    Player thisPlayer;

    void setPanel(Object[] panel, int locX, int locO) {
        this.panel = panel;

        thisPlayer.locationDO = locX;
        thisPlayer.opponent.locationDO = locO;

        thisPlayer.setStartPosition(thisPlayer.locationDO);
        System.out.println(thisPlayer + " - " + thisPlayer.locationDO);
        thisPlayer = thisPlayer.opponent;

        thisPlayer.setStartPosition(thisPlayer.locationDO);
        System.out.println(thisPlayer + " - " + thisPlayer.locationDO);
        thisPlayer.changeStartPosition(thisPlayer.opponent.locationDO);

        thisPlayer = thisPlayer.opponent;
        thisPlayer.changeStartPosition(thisPlayer.opponent.locationDO);
    }

    private void changePlayer() {
        thisPlayer.wasMove = false;
        thisPlayer = thisPlayer.opponent;
        thisPlayer.output.println("YOUR_MOVE");
    }

    private boolean winner(int location) throws NullPointerException, ArrayIndexOutOfBoundsException {
        return panel[location].equals(thisPlayer.opponent);
    }

    private synchronized int direction(String subCommand) { //направление хода
        if (subCommand.startsWith("LEFT"))
            return thisPlayer.locationDO - 1;
        else if (subCommand.startsWith("RIGHT"))
            return thisPlayer.locationDO + 1;
        else if (subCommand.startsWith("UP"))
            return thisPlayer.locationDO - columnLength;
        else if (subCommand.startsWith("DOWN"))
            return thisPlayer.locationDO + columnLength;
        else
            return -1;
    }

    private synchronized boolean thisMove(Player player, String command, PrintWriter output) {
        if (player == thisPlayer) { //если сейчас ходит правильный игрок
            String turn = command.substring(5); //достаем направление
            int location = direction(turn); //определяем координату по направлению

            panel[thisPlayer.locationDO] = null; //удаляем предыдушую точку игрока

            try {
                if (thisPlayer.locationDO % columnLength == 0 && turn.equals("LEFT")) { //проверка для одномерного массива
                    throw new ArrayIndexOutOfBoundsException();
                } else if ((thisPlayer.locationDO + 1) % columnLength == 0 && turn.equals("RIGHT")) { //проверка для одномерного массива
                    throw new ArrayIndexOutOfBoundsException();
                }

                if (panel[location].equals(brick) || panel[location].equals(granite)) { //если препятствие
                    int code = 1; //если препятствие то это код 1
                    output.println("OPEN " + code + " " + turn); //команда открыть клетку по заданному направлению с определенным кодом
                    thisPlayer.opponent.otherPlayerOpened(code, turn); //уведомляем оппонента о результате своего хода

                } else if (panel[location].equals(thisPlayer.opponent) && !thisPlayer.opponent.onRuins) { //если на клетке противник и под ним нет руин, то идем
                    panel[location] = thisPlayer; //игрок встает на клетку
                    if (thisPlayer.onRuins) {
                        panel[thisPlayer.locationDO] = ruin;
                        thisPlayer.onRuins = false;
                    }
                    output.println("VALID_MOVE " + turn); //команда разрешает ход
                    thisPlayer.opponent.otherPlayerMoved(turn, 0); //уведомляем оппонента о результате своего хода
                    thisPlayer.locationDO = location; //запоминаем позицию игрока

                } else if (panel[location].equals(ruin) || (panel[location].equals(thisPlayer.opponent) && thisPlayer.opponent.onRuins)) { //если на клетке осколки или противник под которым руины, то идем
                    panel[location] = thisPlayer; //игрок встает на клетку
                    thisPlayer.onRuins = true; //запоминаем что игрок встал на руины
                    output.println("VALID_MOVE_" + turn); //команда разрешает ход
                    thisPlayer.opponent.otherPlayerMoved(turn, 1); //уведомляем оппонента о результате своего хода с осколками
                    thisPlayer.locationDO = location; //запоминаем позицию игрока
                }

            } catch (ArrayIndexOutOfBoundsException e) { //если вышли за клетку
                int code = 1; //все что за полем - препятствие
                output.println("OPEN " + code + " " + turn); //команда для клиента нарисовать стену (т.к. код 1)
                thisPlayer.opponent.otherPlayerOpened(code, turn); //уведомляем оппонента о результате своего хода

            } catch (NullPointerException e) { //если на клетке пусто, то идем
                panel[location] = thisPlayer; //игрок встает на клетку
                if (thisPlayer.onRuins) {
                    panel[thisPlayer.locationDO] = ruin;
                    thisPlayer.onRuins = false;
                }
                output.println("VALID_MOVE " + turn); //команда разрешает ход
                thisPlayer.opponent.otherPlayerMoved(turn, 0); //уведомляем оппонента о результате своего хода
                thisPlayer.locationDO = location; //запоминаем позицию игрока
            }
            thisPlayer.wasMove = true;
                thisPlayer.output.println("MESSAGE БРОСЬТЕ БОМБУ ИЛИ ПРОДОЛЖИТЕ ПУТЬ");
            return true;
        }
        return false;
    }

    private synchronized boolean thisThrow(Player player, String command, PrintWriter output) {
        if (player == thisPlayer) {
            String turn = command.substring(5); //достаем направление
            int location = direction(turn); //определяем координату по направлению
            String message = null; //сообщение для оппонента о результате своего хода

            try {
                if (thisPlayer.locationDO % columnLength == 0 && turn.equals("LEFT")) { //проверка для одномерного массива
                    throw new ArrayIndexOutOfBoundsException();
                } else if ((thisPlayer.locationDO + 1) % columnLength == 0 && turn.equals("RIGHT")) { //проверка для одномерного массива
                    throw new ArrayIndexOutOfBoundsException();
                }

                if (winner(location)) { //проверка на попадание
                    panel[location] = null;
                    output.println("VICTORY");

                    message = "DEFEAT";

                } else if (panel[location].equals(brick)) { //если кирпич, то ломаем его
                    panel[location] = ruin;
                    output.println("DESTROYED " + turn);

                    message = "OPPONENT_DESTROYED " + turn;

                } else if (panel[location].equals(granite)) { //если гранит
                    output.println("NOT_DESTROYED " + turn);

                    message = "OPPONENT_NOT_DESTROYED " + turn;

                } else if (panel[location].equals(ruin)) { //если осколки
                    output.println("THROW_INTO_THE_VOID_" + turn);

                    message = "OPPONENT_THROW_INTO_THE_VOID_" + turn;
                }

            } catch (NullPointerException e) { //если попали в пустоту
                if (player == thisPlayer) {
                    output.println("THROW_INTO_THE_VOID " + turn);

                    message = "OPPONENT_THROW_INTO_THE_VOID " + turn;
                }
            } catch (ArrayIndexOutOfBoundsException e) { //если стреляем дальше границы поля
                output.println("NOT_DESTROYED " + turn);

                message = "OPPONENT_NOT_DESTROYED " + turn;
            }
            thisPlayer.opponent.otherPlayerThrowed(message); //уведомляем оппонента о результате своего хода
            changePlayer(); // ход переходит оппоненту
            return true;
        }
        return false;
    }

    private class Brick {
    }

    private class Granite {
    }

    private class Ruin {

    }

    Brick brick = new Brick();
    Granite granite = new Granite();
    Ruin ruin = new Ruin();

    class Player extends Thread {
        char mark;
        Game game = new Game();
        Player opponent;
        Socket socket;
        BufferedReader input;
        PrintWriter output;

        int currentLocation;
        int locationDO;

        boolean firstMove = true;

        boolean wasMove = false;

        boolean onRuins = false;

        Player(Socket socket, char mark) {
            this.socket = socket;
            this.mark = mark;
            try {
                input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println("WELCOME " + mark);
                output.println("MESSAGE ЖДИТЕ ПОДКЛЮЧЕНИЯ ПРОТИВНИКА");
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            }
        }


        void setOpponent(Player opponent) {
            this.opponent = opponent;
        }

        void changeStartPosition(int location) {
            if (firstMove) {
                panel[location] = thisPlayer.opponent;
                output.println("OPPONENT_START");
                thisPlayer.firstMove = false;
            }
        }

        void otherPlayerOpened(int code, String turn) {
            output.println("OPPONENT_OPEN " + code + " " + turn);
        }

        void otherPlayerMoved(String turn, int code) {
            if (code == 0) {
                System.out.println("OPPONENT_MOVED ");
                output.println("OPPONENT_MOVED " + turn);
            }
            else {
                System.out.println("OPPONENT_MOVED_");
                output.println("OPPONENT_MOVED_" + turn);
            }
        }

        void otherPlayerThrowed(String message) {
            output.println(message);
        }

        void setStartPosition(int location) {
            if (firstMove) {
                panel[location] = thisPlayer; //ставим игрока на стартовую позицию
                output.println("START");
            }
        }

        public void run() {
            try {
                output.println("MESSAGE ВСЕ ИГРОКИ ПОДКЛЮЧИЛИСЬ К СЕРВЕРУ");
                if (mark == 'X') {
                    output.println("MESSAGE ВАШ ХОД");
                }

                while (true) {
                    String command = input.readLine();
                    if (command != null) {
                        if (command.startsWith("MOVE") && !wasMove) {
                            if (!thisMove(this, command, output)) {
                                output.println("MESSAGE СЕЙЧАС НЕ ВАШ ХОД");
                            }

                        } else if (command.startsWith("BOMB")) {
                            if (!thisThrow(this, command, output)) {
                                output.println("MESSAGE СЕЙЧАС НЕ ВАШ ХОД");
                            }

                        } else if (command.startsWith("CHANGE")) {
                            changePlayer();

                        } else if (command.startsWith("QUIT")) {
                            return;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public String toString() {
        if (thisPlayer != null) {
            return thisPlayer.mark + " has won";
        } else {
            return "game in progress";
        }
    }
}
package pl.edu.pb.wi.fd;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    private List<ChatClient> clients;

    char[] moves = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    boolean isCircleNow = true;

    public ChatServerImpl() throws RemoteException {
        super();
        this.clients = new ArrayList<>();
        System.out.println(getCurrentBoard());
    }

    @Override
    public void registerClient(ChatClient client) throws RemoteException {
        clients.add(client);
        System.out.println("New client registered.");
    }

    @Override
    public void gameMove(int index) throws RemoteException {
        if (moves[index] != 'O' && moves[index] != 'X')
            moves[index] = isCircleNow ? 'O' : 'X';
        String winner;

        if (isThereWinner())
            winner = isCircleNow
                    ? "\tO won!"
                    : "\tX won!";
        else
            winner = "";
        isCircleNow = !isCircleNow;

        for (ChatClient client : clients)
            client.receiveMessage(getCurrentBoard() + winner);
    }

    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServerImpl();
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("ChatServer", server);
            System.out.println("Chat server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentBoard() {
        return String.format(
                """
                         %c | %c | %c
                        ---+---+---
                         %c | %c | %c
                        ---+---+---
                         %c | %c | %c
                        """,
                moves[0],
                moves[1],
                moves[2],
                moves[3],
                moves[4],
                moves[5],
                moves[6],
                moves[7],
                moves[8]);
    }

    private boolean isTrioSame(int uno,
                               int dos,
                               int tres) {
        char a = moves[uno];
        char b = moves[dos];
        char c = moves[tres];
        return a == b && b == c;
    }

    private boolean isThereWinner() {
        return isTrioSame(0, 1, 2)
                || isTrioSame(3, 4, 5)
                || isTrioSame(6, 7, 8)
                || isTrioSame(0, 3, 6)
                || isTrioSame(1, 4, 7)
                || isTrioSame(2, 5, 8)
                || isTrioSame(0, 4, 8)
                || isTrioSame(2, 4, 6);
    }
}

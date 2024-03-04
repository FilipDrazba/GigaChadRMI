package pl.edu.pb.wi.fd;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
    private ChatServer server;

    protected ChatClientImpl(ChatServer server) throws RemoteException {
        super();
        this.server = server;
        server.registerClient(this);
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println("\n"+ message+"\nEnter your move: ");
    }

    public void gameMove(int index) throws RemoteException {
        server.gameMove(index);
    }

    public static void main(String[] args) {
        try {
            ChatServer server = (ChatServer) java.rmi.Naming.lookup("rmi://localhost/ChatServer");
            ChatClient client = new ChatClientImpl(server);

            Scanner scanner = new Scanner(System.in);
            System.out.println(
                    """
                        0 | 1 | 2
                       ---+---+---
                        3 | 4 | 5
                       ---+---+---
                        6 | 7 | 8
                    """
            );

            System.out.print("Enter your move: ");
            while (true) {
                int move = scanner.nextInt();
                client.gameMove(move);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

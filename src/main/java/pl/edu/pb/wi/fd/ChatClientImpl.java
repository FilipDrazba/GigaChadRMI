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
        System.out.println("\nReceived message: " + message + "\nEnter your message: ");
    }

    public void sendMessage(String message) throws RemoteException {
        server.broadcastMessage(message);
    }

    public static void main(String[] args) {
        try {
            ChatServer server = (ChatServer) java.rmi.Naming.lookup("rmi://localhost/ChatServer");
            ChatClient client = new ChatClientImpl(server);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter your message: ");
                String message = scanner.nextLine();
                client.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

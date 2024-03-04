package pl.edu.pb.wi.fd;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    private List<ChatClient> clients;

    public ChatServerImpl() throws RemoteException {
        super();
        this.clients = new ArrayList<>();
    }

    @Override
    public void registerClient(ChatClient client) throws RemoteException {
        clients.add(client);
        System.out.println("New client registered.");
    }

    @Override
    public void broadcastMessage(String message) throws RemoteException {
        System.out.println("Broadcasting message: " + message);
        for (ChatClient client : clients) {
            client.receiveMessage(message);
        }
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
}

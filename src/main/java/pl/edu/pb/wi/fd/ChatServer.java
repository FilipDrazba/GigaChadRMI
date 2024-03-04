package pl.edu.pb.wi.fd;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
    void registerClient(ChatClient client) throws RemoteException;
    void gameMove(int index) throws RemoteException;
}

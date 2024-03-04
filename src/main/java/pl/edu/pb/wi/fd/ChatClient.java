package pl.edu.pb.wi.fd;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
    void receiveMessage(String message) throws RemoteException;
    void sendMessage(String message) throws RemoteException;
}

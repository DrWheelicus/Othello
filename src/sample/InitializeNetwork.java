package sample;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class InitializeNetwork {

    public ConnectionThread connected = new ConnectionThread();
    private Consumer<Serializable> received;

    public InitializeNetwork(Consumer<Serializable> received){
        this.received = received;
        connected.setDaemon(true);
    }

    void send(Serializable data) throws Exception{
        System.out.println(data);
        connected.toServer.writeObject(data);
    }

    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();

    public class ConnectionThread extends Thread {
        public Socket socket;
        public ObjectOutputStream toServer;
        public ObjectInputStream fromServer;

        @Override
        public void run(){
            try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                 Socket socket = isServer() ? server.accept(): new Socket(getIP(), getPort());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                this.socket = socket;
                this.toServer = out;
                this.fromServer = in;
                socket.setTcpNoDelay(true);

                while (true){
                    Serializable data = (Serializable) in.readObject();
                    received.accept(data);
                }
            } catch (Exception e) {
                received.accept("Connection Closed");
            }
        }
    }


    void start() throws Exception{
        connected.start();
    }
}

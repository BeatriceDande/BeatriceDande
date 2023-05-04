import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class Main {


    public static void main(String[] args) {

        boolean done = true;

        try {
            final int port = 8080;
            ServerSocket server = new ServerSocket(port);
            while (done) {
                if (port != 8080) {
                    System.out.println("Invalid port");
                }
                Socket connection = server.accept();
                HandleConnection handleConnection = new HandleConnection(connection);
                Thread clientThread = new Thread(handleConnection);
                clientThread.start();


            }
        }
        catch(IOException e){
            System.out.println("Failed/Interrupted I/O operations");
        }

    }
}



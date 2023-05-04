import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class HandleConnection implements Runnable{
    private final Socket connection_;
    HandleConnection(Socket sc){
        connection_ = sc;
    }

    @Override
    public void run() {


        ChatServerRequest request = new ChatServerRequest(connection_);
        request.HTTPRequest();

    }

}


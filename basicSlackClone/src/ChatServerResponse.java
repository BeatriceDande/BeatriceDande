import java.io.*;
import java.net.Socket;

public class ChatServerResponse {
    OutputStream response_ = null;
    String ErrorMessage_ = "HTTP/1.1 404 File Not Found \n";
    String successMessage_ = "HTTP/1.1 200 ok\n";
    Socket connection_=null;

    File path_;

    public ChatServerResponse( Socket connection,File path) {
        path_ = path;
      connection_= connection;
       try {
           System.out.println("making http response");
           if (!path_.exists()) {
               System.out.println("error canExecute");
               response_ = connection_.getOutputStream();
               response_.write((ErrorMessage_).getBytes());
               response_.write(("\n").getBytes());
           } else {
               System.out.println("sending http response");
               response_ = connection_.getOutputStream();
               FileInputStream fileInputStream = new FileInputStream(path_);
               response_.write((successMessage_).getBytes());
               response_.write(("\n").getBytes());
               response_.flush();
               fileInputStream.transferTo(response_);
           }
           response_.flush();
           connection_.close();
       }

                catch (FileNotFoundException e) {
                System.out.println("File not found!");

                }

                catch (IOException e) {
                System.out.println("Failed/Interrupted I/O operations");
                }

                }

    //Todo: add if message == how many people are in the room? count all the
    //Todo: people in the arraylist and send a json message telling everyone in the
    //Todo: room the number of people in the room

}

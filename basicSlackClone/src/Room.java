import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class Room {
    public static ArrayList<Room> rooms_ = new ArrayList<>();
    private ArrayList<Socket> clients_=new ArrayList<>();

    String roomName_;


    public Room(String roomName)  {
        roomName_ = roomName;

    }

    public String GetRoomName(String roomName) {
        return roomName_;
    }

//this function creates a new room and adds it to the room arraylist,
// but checks to see if the room exists first before creating it
    public synchronized static Room GetRoom(String roomName) {

        Room myRoom=null;
        if (rooms_.size()==0) {
            Room newRoom = new Room(roomName);
            rooms_.add(newRoom);
            return newRoom;
        } else {
            for (Room rm : rooms_) {
                if (rm.roomName_.equals(roomName)) {
                    return rm;
                }
            }

            myRoom = new Room(roomName);
            rooms_.add(myRoom);
            return myRoom;
        }
    }



    public synchronized void AddClient(Socket sc) {

        this.clients_.add(sc);

    }

    public  synchronized void RemoveClient(Socket sc) {
        this.clients_.remove(sc);

    }

//loops through the arraylist of clients in a room and sends the message to all
//this function removes a client to a room
    public synchronized void SendMessage(String jsonMessage) throws IOException {
        try {
            for (Socket sc : clients_) {

                OutputStream outputStream = sc.getOutputStream();
                DataOutputStream dataOutStream = new DataOutputStream(outputStream);

                if (jsonMessage.length() < 126) {
                    dataOutStream.writeByte(0x81);
                    dataOutStream.writeByte(jsonMessage.length());
                    dataOutStream.write(jsonMessage.getBytes());
                    dataOutStream.flush();

                } else if (jsonMessage.length() < 65535) {
                    dataOutStream.writeByte(0x81);
                    dataOutStream.writeByte(126);
                    dataOutStream.writeByte(jsonMessage.length());
                    dataOutStream.write(jsonMessage.getBytes());
                    dataOutStream.write(("\r\n\r\n").getBytes());
                    dataOutStream.flush();

                } else {
                    dataOutStream.writeByte(0x81);
                    dataOutStream.writeByte(127);
                    dataOutStream.writeByte(jsonMessage.length());
                    dataOutStream.write(jsonMessage.getBytes());
                    dataOutStream.write(("\r\n\r\n").getBytes());
                    dataOutStream.flush();

                }

            }
        }
        catch (IOException e) {
            System.out.println("Failed/Interrupted I/O operations");
        }
    }

}






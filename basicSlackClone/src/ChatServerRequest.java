
import netscape.javascript.JSObject;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ChatServerRequest {


    //this constructor takes a socket/connection; reads the request header,
    // extract the file name and listens to the request
    public ChatServerRequest(Socket connection) {
        SocketConnection_ = connection;
    }

    //this method reads the request header,
    // extract the file name and listens to the request
   public void HTTPRequest() {
        try {
            requestInputStreamReader_ = new InputStreamReader(SocketConnection_.getInputStream());
            bufferedReader_ = new BufferedReader(requestInputStreamReader_);
            headerContent_ = new HashMap<>();
            String line;
            System.out.println("Requested line");
            line = bufferedReader_.readLine();
            String[] line1 = line.split(" ");
            fileName_ = line1[1];

            if (fileName_.equals("/")) {
               // fileName_ = "index.html";
                fileName_ = "Chat.html";
            }
            else {
                fileName_ = "Chat.html";
            }
            String file = "resources/" + fileName_;
            path_ = new File(file);
            System.out.println(path_);


            while (!line.isEmpty()) {
                line = bufferedReader_.readLine();
                String[] keyAndValue = line.split(": ", 2);
                if (keyAndValue.length > 1) {
                    String key = keyAndValue[0];
                    String value = keyAndValue[1];
                    headerContent_.put(key, value);
                    System.out.println(key);
                    System.out.println(value);
                }
            }
            generateKey(headerContent_);

        } catch (FileNotFoundException e) {
        } catch (IOException e) {

        }
    }


    private void generateKey(HashMap<String,String>map) {
        try {
            if (map.containsKey("Sec-WebSocket-Key")) {
                System.out.println("found sec-websocket-key");
                System.out.println("trying to connect to ws");
                String requestKey = map.get("Sec-WebSocket-Key");
                // String testRequestKey = "dGhlIHNhbXBsZSBub25jZQ==";
                System.out.println("Request Key:" + requestKey);
                String responseKey = requestKey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
                System.out.println("response key:" + responseKey);

                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(responseKey.getBytes());
                byte[] digest = md.digest();
                acceptKey_ = Base64.getEncoder().encodeToString(digest);
                System.out.println("My ecripted key: " + acceptKey_);
                isAWebsocketRequest = true;
            }
            else {
                res_ = new ChatServerResponse(SocketConnection_,path_);
            }


            conductHandshake(isAWebsocketRequest);

        } catch (NoSuchAlgorithmException e) {


        }
    }


    //handshake to accept websocket connection
    private void conductHandshake(boolean isWS)  {
try {
    if (isAWebsocketRequest) {
        System.out.println("is a websocket");
        OutputStream response = SocketConnection_.getOutputStream();
        response.write(("HTTP/1.1 101 Switching Protocols\r\n" + "Upgrade: websocket\r\n"
                + "Connection: Upgrade\r\n" + "Sec-WebSocket-Accept: " + acceptKey_ + "\r\n\r\n").getBytes());
        response.flush();
    }
    readMessage();

}
      catch (IOException e ){

      }
    }



    //read contents of the actual message
    private void readMessage() throws IOException {

        while (true) {
            InputStream inputStream = SocketConnection_.getInputStream();
            dataInputStream_ = new DataInputStream(inputStream);
            byte[] data = dataInputStream_.readNBytes(2);
            int opcode = data[0] & 0x0F;
            if (opcode == 8) {
                json_ = "{\"type\": \"leave\", \"room\": \"" + roomName_ + "\", \"user\": \"" + name_ + "\"}";

            }

            payloadLength_ = data[1] & 0x7F;
            actualPayloadLength_ = 0;

            if (payloadLength_ <= 125 || payloadLength_ != 0) {
                payloadLength_ = data[1] & 0x7F;
                actualPayloadLength_ = payloadLength_;

            } else if (payloadLength_ == 126) {
                actualPayloadLength_ = dataInputStream_.readShort();
                System.out.println("payload length 126 - actual: " + actualPayloadLength_);
            } else if (payloadLength_ > 126) {

                actualPayloadLength_ = dataInputStream_.readLong();
                System.out.println("payload length 126 - actual: " + actualPayloadLength_);
            }
            decodeMessage(actualPayloadLength_);
        }



    }

//decode the actual message
    private void decodeMessage(Long myPayloadLength) throws IOException {
        byte[] decodedMessage = new byte[(int) actualPayloadLength_];

        byte[] maskingKey = dataInputStream_.readNBytes(4);
        byte[] encodedMessage = dataInputStream_.readNBytes((int) actualPayloadLength_);
        System.out.println("Encoded message length: " + encodedMessage.length);
        for (int i = 0; i < encodedMessage.length; i++) {
            decodedMessage[i] = (byte) (encodedMessage[i] ^ maskingKey[i % 4]);
        }
        actualMessage_ = new String(decodedMessage, StandardCharsets.UTF_8);

        parseMessage(actualMessage_);
    }


//parse the message
    private void parseMessage(String myMessage) throws IOException {


        OutputStream outputStream = SocketConnection_.getOutputStream();
        dataOutStream_ = new DataOutputStream(outputStream);

        System.out.println("My message:" + actualMessage_);
        String[] str = actualMessage_.split(" ");
        messagePieces_ = Arrays.asList(str);
        generateJsonMessage(messagePieces_);
    }



//generate a json like string to send as a chat message
    //send message to everyone in the room if anyone sends a chat or joins the room
    private void generateJsonMessage(List<String> myMessagePieces) throws IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        System.out.println("message below is of type join");

        if (myMessagePieces.size() > 0 && myMessagePieces.get(0).equals("join")) {
            name_ = myMessagePieces.get(1);
            System.out.println("Name: " + name_);
            roomName_ = myMessagePieces.get(2);
            System.out.println("Room: " + roomName_);

            json_ = "{\"type\": \"join\", \"room\": \"" + roomName_ + "\", \"user\": \"" + name_ + "\", \"time\":\""+now+"\"}";
            System.out.println("My json string:" + json_);
            rm_ = Room.GetRoom(roomName_);
            rm_.AddClient(SocketConnection_);

        } else if (myMessagePieces.size() > 0 && myMessagePieces.get(0).equals("message")) {
            name_ = myMessagePieces.get(1);
            roomName_ = myMessagePieces.get(2);
            System.out.println("Name: " + name_);
            rm_ = Room.GetRoom(roomName_);
            String messageSent = "";
            for (int i = 3; i < myMessagePieces.size(); i++) {
                messageSent = messageSent + " " + myMessagePieces.get(i);

            }
            json_ = "{ \"type\": \"message\", \"user\": \"" + name_ + "\"," +
                    " \"room\": \"" + roomName_ + "\",\"time\":\""+now+ "\", \"message\": \"" + messageSent + "\"}";
            System.out.println("My json string:" + json_);


        }
        if (myMessagePieces.size() > 0 && myMessagePieces.get(0).equals("leave")) {
            name_ = myMessagePieces.get(1);
            System.out.println("Name: " + name_);
            roomName_ = myMessagePieces.get(2);


            json_ = "{\"type\": \"leave\", \"room\": \"" + name_ + "\", \"user\": \"" + roomName_ + "\"}";
            rm_ = Room.GetRoom(roomName_);
            rm_.SendMessage(json_);
        }
        rm_.SendMessage(json_);
    }



    String fileName_=null;
    Room rm_ = null;

    InputStreamReader requestInputStreamReader_=null;
    BufferedReader bufferedReader_=null;

    public String GetFileName() {
        return fileName_;
    }
    ChatServerResponse res_=null;
    HashMap<String, String> headerContent_=null;
    Socket SocketConnection_=null;
    String acceptKey_=null;
    DataInputStream dataInputStream_=null;
    DataOutputStream dataOutStream_=null;
    boolean isAWebsocketRequest = false;
    String json_=null;
    File path_=null;
    String actualMessage_=null;
    List<String> messagePieces_= new ArrayList<>();


    public File GetFilePath() {
        return path_;
    }

    public String GetActualMessage() {
        return actualMessage_;
    }

    public String GetJson() {
        return json_;
    }

    String roomName_=null;
    String name_=null;
    long payloadLength_ =0;

    long actualPayloadLength_ = 0;

    public String GetAcceptKey() {
        return acceptKey_;
    }

    boolean GetIsWebSocket() {
        return isAWebsocketRequest;
    }
}


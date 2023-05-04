import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DNSServer {
    public static byte [] data = new byte[512];


    //This class opens up a UDP socket (DatagramSocket class in Java), and listen for requests.
    // When it gets one, it looks at all the questions in the request.
    //  If there is a valid answer in cache, add that the response,
    //   otherwise create another UDP socket to forward the request Google (8.8.8.8)
    //    and then await their response. Once you've dealt with all the questions,
    //     send the response back to the client.
    //
    //Note: dig sends an additional record in the "additionalRecord" fields with a type of 41.
    // You're supposed to send this record back in the additional record part of your response as well.
    //
    //Note, that in a real server, the UDP packets you receive could be client requests
    // or google responses at any time. For our basic testing you can assume that the next UDP packet
    //  you get after forwarding your request to Google will be the response from Google.
    //   To be more robust, you can look at the ID in the request, and keep track
    //   of your "in-flight" requests to Google, but you don't need to do that for the assignment

    public static void main(String[] args) throws IOException {

        System.out.println("server about to start!");
        boolean running = true;

        DatagramSocket datagramSocket;
        try {
            datagramSocket = new DatagramSocket(8053);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while (running) {
            //incoming request decoded
            byte[] buf = new byte[512];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            datagramSocket.receive(packet);
            //data =packet.getData();
            data =Arrays.copyOfRange(packet.getData(),0,packet.getData().length);
            DNSMessage message=DNSMessage.decodeMessage(data);
            DNSQuestion question = message.dnsQuestions.get(0);

            // request in cache, build response and send to client
           if(DNSCache.contains(question)){

               DNSRecord record = DNSCache.queryDNSCache(question);
               DNSMessage responseMessage =DNSMessage.buildResponse(message,record);
               byte[] response  = responseMessage.toBytes();
               DatagramPacket toUser = new DatagramPacket(response,response.length,packet.getAddress(), packet.getPort());
               datagramSocket.send(toUser);

           }
           else {
                //request not in local cache, forward request to google
               DatagramPacket toGoogle = new DatagramPacket(data, data.length, Inet4Address.getByName("8.8.8.8"), 53);
               datagramSocket.send(toGoogle);
               byte[] buf2 = new byte[512];
               DatagramPacket fromGoogle = new DatagramPacket(buf2, buf2.length);
               datagramSocket.receive(fromGoogle);

              // byte[] googleData =fromGoogle.getData();
               byte[] googleData =Arrays.copyOfRange (fromGoogle.getData(),0,fromGoogle.getData().length) ;
               DatagramPacket toUser = new DatagramPacket(googleData, googleData.length, packet.getAddress(), packet.getPort());
               datagramSocket.send(toUser);
               //send response from google direct to client
//               DatagramPacket toUser = new DatagramPacket(googleData, googleData.length, packet.getAddress(), packet.getPort());
//               datagramSocket.send(toUser);

                //add a valid response from google to cache
               DNSMessage message2 = DNSMessage.decodeMessage(googleData);
               DNSQuestion question2 = message2.dnsQuestions.get(0);
               if(message2.answers.size()!=0) {
                   DNSRecord record = message2.answers.get(0);
                   DNSCache.insertDNSCache (question2, record);
               }

               else {
                   System.out.println("No Response, Not Cached");
               }
           }

        }
    }


}

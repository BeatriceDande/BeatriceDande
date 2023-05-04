import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DNSMessage {
  ByteArrayInputStream inputStream;
   ByteArrayOutputStream outputStream;
   DNSHeader dnsHeader = new DNSHeader();


    ArrayList<DNSQuestion> dnsQuestions = new ArrayList<>();
    ArrayList<DNSRecord> answers = new ArrayList<>();
    ArrayList<DNSRecord>authorityRecords =new ArrayList<>();
    ArrayList<DNSRecord>additionalRecords =new ArrayList<>();
    HashMap<String, Integer> dnsLocation = new HashMap<>();
 ArrayList<String> domainNamePieces;
    DNSHeader getDnsHeader(){
     return dnsHeader;
    }
    String getDomainName(){
     return  domainName;
    }

    byte[] dataBytes;
    byte[] responseData;
    String domainName = "";

    // Corresponds to an entire DNS Message. It contains:
    //the DNS Header
    //an array of questions
    //an array of answers
    //an array of authority records
    //an array of additional records

    static DNSMessage decodeMessage(byte[] bytes) throws IOException {


     DNSMessage message = new DNSMessage();
     message.dataBytes = bytes;
     message.inputStream = new ByteArrayInputStream(bytes);
     message.dnsHeader = DNSHeader.decodeHeader(message.inputStream);
     int numberOfQuestions=message.dnsHeader.QDCOUNT;
     int numberOfAnswers = message.dnsHeader.ANCOUNT;
     int numOfauthorityRecorsd = message.dnsHeader.NSCOUNT;
     int numOfadditionalRecords = message.dnsHeader.ARCOUNT;

     for(int i=0; i<numberOfQuestions; i++){
      DNSQuestion dnsQuestion = DNSQuestion.decodeQuestion(message.inputStream,message);
      message.dnsQuestions.add(dnsQuestion);
     }

     for(int i=0; i<numberOfAnswers; i++){
      DNSRecord dnsRecord = DNSRecord.decodeRecord(message.inputStream,message);
      message.answers.add(dnsRecord);
     }
     for(int i=0; i<numOfauthorityRecorsd; i++){
      DNSRecord dnsRecord = DNSRecord.decodeRecord(message.inputStream,message);
      message.authorityRecords.add(dnsRecord);
     }
     for(int i=0; i<numOfadditionalRecords; i++){
      DNSRecord dnsRecord =DNSRecord.decodeRecord(message.inputStream,message);
      message.additionalRecords.add(dnsRecord);
     }

    return message;
    }
    //reads the pieces of a domain name starting from the current position of the input stream
    ArrayList<String> readDomainName(InputStream inputStream) throws IOException {
     domainNamePieces = new ArrayList<>();
     int length1 = inputStream.read();
     while (length1!=0){
      byte[] bytes = inputStream.readNBytes(length1);
      String str = new String(bytes);
      domainNamePieces.add(str);
      length1=inputStream.read();
     }

     return domainNamePieces;
    }
    //reads the pieces of a domain name starting from the current position of the input stream
    //used when there's compression, and we need to find the domain from earlier in the message.
    // This method should make a ByteArrayInputStream that starts at the specified byte and call
    //  the other version of this method

     ArrayList<String> readDomainName(int firstByte) throws IOException {
     ByteArrayInputStream inputStream2 = new ByteArrayInputStream(DNSServer.data);
     inputStream2.readNBytes((byte)firstByte);
     domainNamePieces = readDomainName(inputStream2);
    return domainNamePieces;


    }
   // build a response based on the request and the answers you intend to send back.
    static DNSMessage buildResponse(DNSMessage request, DNSRecord answer) throws IOException {
     DNSMessage response = new DNSMessage();
     response.dnsHeader = DNSHeader.buildHeaderForResponse(request);
     response.dnsQuestions =request.dnsQuestions;
     response.answers.add (answer);
     response.authorityRecords=request.authorityRecords;
     response.additionalRecords = request.additionalRecords;
    return response;
    }

    //get the bytes to put in a packet and send back
   public  byte[] toBytes() throws IOException {
    outputStream=new ByteArrayOutputStream();

     dnsHeader.writeBytes(outputStream);
     for(DNSQuestion qnt:dnsQuestions){
      qnt.writeBytes(outputStream,dnsLocation);
     }
     for (DNSRecord record:answers){
      record.writeBytes(outputStream,dnsLocation);
     }
     for (DNSRecord record:authorityRecords){
      record.writeBytes(outputStream,dnsLocation);
     }
     for (DNSRecord record:additionalRecords){
      record.writeBytes(outputStream,dnsLocation);
     }
     responseData=outputStream.toByteArray();
    return responseData;
    }
    //If this is the first time we've seen this domain name in the packet,
    // write it using the DNS encoding (each segment of the domain prefixed with its length, 0
    //  at the end), and add it to the hash map.
    // Otherwise, write a back pointer to where the domain has been seen previously.
   void writeDomainName(ByteArrayOutputStream outputStream,
                                HashMap<String,Integer> domainNameLocations,
                                ArrayList<String> domainPieces) throws IOException {

     String domainName= joinDomainName(domainPieces);

     byte firstByte;

     if(!domainNameLocations.containsKey(domainName)){
      domainNameLocations.put(domainName, outputStream.size());
      for(String str: domainPieces){
       firstByte= (byte)(str.getBytes().length);
       outputStream.write(firstByte);

       outputStream.write(str.getBytes());
      }
      byte terminatingZero =(byte) 0;
      outputStream.write(terminatingZero);

     }
     else {

      int offset = (domainNameLocations.get(domainName));
      short twobytes = (short)(offset|0xC000);
      byte b1 =(byte) (twobytes>>>8);
      byte b2 = (byte) twobytes;

      outputStream.write(b1);
      outputStream.write(b2);
     }

    }


    //join the pieces of a domain name with dots ([ "utah", "edu"] -> "utah.edu" )
    String joinDomainName(ArrayList<String> pieces){
     if (pieces.size()>0){
      StringBuilder sb = new StringBuilder();
      for(String str: pieces){
       sb.append(str).append(".");
      }
      domainName = sb.deleteCharAt(sb.length()-1).toString();
     }

    return  domainName;
    }


 @Override
 public String toString() {
  return "DNSMessage{" +
          "inputStream=" + inputStream +
          ", outputStream=" + outputStream +
          ", dnsHeader=" + dnsHeader +
          ", dnsQuestions=" + dnsQuestions +
          ", answers=" + answers +
          ", authorityRecords=" + authorityRecords +
          ", additionalRecords=" + additionalRecords +
          ", dnsLocation=" + dnsLocation +
          ", domainNamePieces=" + domainNamePieces +
          ", dataBytes=" + Arrays.toString(dataBytes) +
          ", responseData=" + Arrays.toString(responseData) +
          ", domainName='" + domainName + '\'' +
          '}';
 }
}

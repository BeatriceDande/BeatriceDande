import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DNSQuestion {
    ArrayList<String> QNAME;
    int QTYPE;
    int QCLASS;
    DNSMessage dnsMessage;


    //This class represents a client request.


    //read a question from the input stream. Due to compression,
    // you may have to ask the DNSMessage containing this question to read some of the fields.
    static DNSQuestion decodeQuestion(InputStream inputStream, DNSMessage message) throws IOException {

        DNSQuestion dnsQuestion= new DNSQuestion();
        dnsQuestion.dnsMessage = message;

        dnsQuestion.QNAME = dnsQuestion.dnsMessage.readDomainName(inputStream);
        dnsQuestion.QTYPE =(inputStream.read())<<8|(inputStream.read());
        dnsQuestion.QCLASS = (inputStream.read())<<8|(inputStream.read());

    return dnsQuestion;
    }

    //Writes the question bytes which will be sent to the client.
    // The hash map is used for us to compress the message.
    void writeBytes(ByteArrayOutputStream outputStream, HashMap<String,Integer> domainNameLocations) throws IOException {

        dnsMessage.writeDomainName(outputStream,domainNameLocations,QNAME);

       byte qtypefirstByte= (byte)(QTYPE>>>8);
       byte qtypesecondByte = (byte)QTYPE;
        byte qclassfirstByte= (byte)(QCLASS>>>8);
        byte qclasssecondByte = (byte)QCLASS;

           outputStream.write(qtypefirstByte);
           outputStream.write(qtypesecondByte);
           outputStream.write(qclassfirstByte);
           outputStream.write(qclasssecondByte);

    }


    @Override
    public String toString() {
        return "DNSQuestion{" +
                "QNAME=" + QNAME +
                ", QTYPE=" + QTYPE +
                ", QCLASS=" + QCLASS +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNSQuestion that = (DNSQuestion) o;
        return QTYPE == that.QTYPE && QCLASS == that.QCLASS && Objects.equals(QNAME, that.QNAME);
    }

    @Override
    public int hashCode() {
        return Objects.hash(QNAME, QTYPE, QCLASS);
    }
}

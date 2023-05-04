import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DNSRecord {
    ArrayList<String> NAME;
    int TYPE;
    int CLASS;
    int TTL;
    int RDLENGTH;
    int OFFSET;
    byte[] RDATA;

    double currentTime;
    long timeCached;
    DNSMessage dnsMessage;

    //Everything after the header and question parts of the DNS message are stored as records.
    //has all the fields listed in the spec


    //decodes a record
    static DNSRecord decodeRecord(InputStream inputStream, DNSMessage message) throws IOException {

        DNSRecord dnsRecord = new DNSRecord();
        dnsRecord.dnsMessage = message;
        inputStream.mark(0);
        int firstByte = inputStream.read();
        int firstBit = firstByte >>> 6;
        if ((firstBit != 0x3) ) {
            inputStream.reset();
            dnsRecord.NAME = dnsRecord.dnsMessage.readDomainName(inputStream);
        }
        else {

            dnsRecord.OFFSET = (firstByte ^ 0xC0) << 8 | inputStream.read();

            dnsRecord.NAME = dnsRecord.dnsMessage.readDomainName(dnsRecord.OFFSET);

        }

        dnsRecord.TYPE = inputStream.read() << 8 | inputStream.read();
        dnsRecord.CLASS = inputStream.read() << 8 | inputStream.read();
        dnsRecord.TTL = inputStream.read() << 24 | inputStream.read() << 16 |
                inputStream.read() << 8 | inputStream.read();
        dnsRecord.RDLENGTH = inputStream.read() << 8 | inputStream.read();
        int RDataLength = dnsRecord.RDLENGTH;

        dnsRecord.RDATA = inputStream.readNBytes(RDataLength);

        return dnsRecord;
    }

    //writes a record data to an outputstream
    void writeBytes(ByteArrayOutputStream outputStream, HashMap<String, Integer> domainNameLocations) throws IOException {
        byte typeFirst = (byte) (TYPE >>> 8);
        byte typeSecond = (byte) TYPE;
        byte classFirst = (byte) (CLASS >>> 8);
        byte classSecond = (byte) CLASS;
        byte ttlFisrt = (byte) (TTL >>> 24);
        byte ttlSec = (byte) (TTL >>> 16);
        byte ttlThird = (byte) (TTL >>> 8);
        byte ttlFouth = (byte) TTL;
        byte rdlengthFirst = (byte) (RDLENGTH >>> 8);
        byte redlengthSec = (byte) RDLENGTH;
        byte[] rdata = RDATA;
        dnsMessage.writeDomainName(outputStream, domainNameLocations, NAME);
        outputStream.write(typeFirst);
        outputStream.write(typeSecond);
        outputStream.write(classFirst);
        outputStream.write(classSecond);
        outputStream.write(ttlFisrt);
        outputStream.write(ttlSec);
        outputStream.write(ttlThird);
        outputStream.write(ttlFouth);
        outputStream.write(rdlengthFirst);
        outputStream.write(redlengthSec);
        outputStream.write(rdata);

    }


    //returns whether the creation date + the time to live is after the current time.
    boolean isExpired() {

        if (TTL == 0) {
            return false;
        } else return currentTime - timeCached > TTL;


    }

    @Override
    public String toString() {
        return "DNSRecord{" +
                "NAME=" + NAME +
                ", TYPE=" + TYPE +
                ", CLASS=" + CLASS +
                ", TTL=" + TTL +
                ", RDLENGTH=" + RDLENGTH +
                ", OFFSET=" + OFFSET +
                ", RDATA=" + Arrays.toString(RDATA) +
                ", currentTime=" + currentTime +
                ", timeChached=" + timeCached +
                '}';
    }

}

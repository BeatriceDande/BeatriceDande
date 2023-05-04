import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class DNSHeader {

   // private static


     short ID; //16 bits 1st 2bytes

     int QR; //1st bit of the 3rd byte
     int Opcode; //2-5 bit of 3rd byte
     int AA; //6th bit of 3rd byte
     int TC; //7th bit of 3rd byte
     int RD;//8th bit of 3rd byte
     int RA;//9th bit of 3rd byte
     int Z;//10th-12th bit of 4th byte
     int AD;
     int CD;
    int tag;
     int RCODE; //last 4 bits of 4th byte
     int QDCOUNT; //5&6th byte
     int ANCOUNT; //7th&8th byte
     int NSCOUNT; //9th&10th byte
     int ARCOUNT; //11th&12th byte






    //reads and decodes the dns header.
    static DNSHeader decodeHeader(ByteArrayInputStream  inputStream) throws IOException {
        DNSHeader requestHeader= new DNSHeader();
        requestHeader.ID =(short)((inputStream.read())<<8|(inputStream.read()));
        requestHeader.tag  =(inputStream.read())<<8|(inputStream.read());
        requestHeader.QDCOUNT =(inputStream.read())<<8|(inputStream.read());
        requestHeader.ANCOUNT =(inputStream.read())<<8|(inputStream.read());
        requestHeader.NSCOUNT =(inputStream.read())<<8|(inputStream.read());
        requestHeader.ARCOUNT =(inputStream.read())<<8|(inputStream.read());


        requestHeader.QR = requestHeader.tag>>>15;
        requestHeader.Opcode = (requestHeader.tag&0x7800)>>>11;
        requestHeader.AA=(requestHeader.tag&0x0400)>>>10;

        requestHeader.TC =(requestHeader.tag&0x0200)>>>9;
        requestHeader.RD=(requestHeader.tag&0x0100)>>>8;
        requestHeader.RA =(requestHeader.tag&0x0080)>>>7;
        requestHeader.Z=(requestHeader.tag&0x0040)>>>6;
        requestHeader.AD=(requestHeader.tag&0x0020)>>>5;
        requestHeader.CD =(requestHeader.tag&0x0010)>>>4;
        requestHeader.RCODE=requestHeader.tag&0x000F;



    return requestHeader;
    }


    //creates the header for the response.
    static DNSHeader buildHeaderForResponse(DNSMessage request ){
        DNSHeader header= request.getDnsHeader();
        header.QR =1;
        header.ANCOUNT =1;
        return header;
    }

   // encodes the header to bytes to be sent back to the client.
   void writeBytes(ByteArrayOutputStream outputStream){

        byte responseTag1  = (byte)(QR<<7| Opcode<<3|AA<<2|
                TC<<1|RD);
       byte responseTag2 = (byte)(RA<<7|Z<<6|AD<<5
                |CD<<4|RCODE);

        byte Id1 = (byte)(ID>>>8);
        byte Id2 = (byte)(ID&0xFF);
        byte QDcount1 = (byte)(QDCOUNT>>>8);
        byte QDcount2 = (byte)(QDCOUNT);
        byte ANcount1 = (byte)(ANCOUNT>>>8);
        byte ANcount2 = (byte)ANCOUNT;
        byte NScount1 = (byte)(NSCOUNT>>>8);
        byte NScount2 = (byte)NSCOUNT;

        byte ARcount1 = (byte)(ARCOUNT>>>8);
        byte ARcount2 = (byte)ARCOUNT;

        outputStream.write(Id1);
        outputStream.write(Id2);
        outputStream.write(responseTag1);
        outputStream.write(responseTag2);
        outputStream.write(QDcount1);
        outputStream.write(QDcount2);
        outputStream.write(ANcount1);
        outputStream.write(ANcount2);
        outputStream.write(NScount1);
        outputStream.write(NScount2);
        outputStream.write(ARcount1);
        outputStream.write(ARcount2);

    }


    //Return a human-readable string version of a header object.
    @Override
    public String toString() {
        return "DNSHeader{" +
                "ID=" + ID +
                ", QR=" + QR +
                ", Opcode=" + Opcode +
                ", AA=" + AA +
                ", TC=" + TC +
                ", RD=" + RD +
                ", RA=" + RA +
                ", Z=" + Z +
                ", AD=" + AD +
                ", CD=" + CD +
                ", RCODE=" + RCODE +
                ", QDCOUNT=" + QDCOUNT +
                ", ANCOUNT=" + ANCOUNT +
                ", NSCOUNT=" + NSCOUNT +
                ", ARCOUNT=" + ARCOUNT +
                '}';
    }



}


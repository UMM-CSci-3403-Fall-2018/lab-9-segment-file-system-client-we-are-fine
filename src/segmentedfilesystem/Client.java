package segmentedfilesystem;

import java.util.ArrayList;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Client {

    ArrayList<byte[]> heap = new ArrayList<byte[]>();
    ArrayList<byte[]> lastPackets = new ArrayList<byte[]>();
    ArrayList<byte[]> headerPackets = new ArrayList<byte[]>();
    int numPackets = 0;
    
    public int getPacketNumber(byte[] received) {

        int packetByte1 = received[2];
        int packetByte2 = received[3];

        if (packetByte2 < 0) {
            packetByte2 += 256;
        }

        int packetNumber = 256 * packetByte1 + packetByte2;

        System.out.println("packet number: " + packetNumber);

        return packetNumber + 1; // The packet count starts at 0, so 1 is added
    }


    public void checkPacketType(byte[] received) {

        if (received[0] % 2 == 1) {

            if (received[0] % 4 == 3) {
                lastPackets.add(received);
                numPackets += getPacketNumber(received);
                heap.add(received);

            } else {
                heap.add(received);
            }
        } else {
            System.out.println("This should be a header packet");
            headerPackets.add(received);
        }
    }
}

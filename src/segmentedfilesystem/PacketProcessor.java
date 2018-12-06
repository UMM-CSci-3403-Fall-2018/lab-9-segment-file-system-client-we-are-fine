package segmentedfilesystem;

import java.util.ArrayList;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class PacketProcessor {

    ArrayList<byte[]> heap = new ArrayList<byte[]>();
    ArrayList<byte[]> lastPackets = new ArrayList<byte[]>();
    ArrayList<byte[]> headerPackets = new ArrayList<byte[]>();
    int numPackets = 0;

    ArrayList<byte[]> file1 = new ArrayList<byte[]>();
    ArrayList<byte[]> file2 = new ArrayList<byte[]>();
    ArrayList<byte[]> file3 = new ArrayList<byte[]>();

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
//            System.out.println("This should be a header packet");
            headerPackets.add(received);
        }
    }

    public void partitionFiles() {

        int file1ID = headerPackets.get(0)[1];
        int file2ID = headerPackets.get(1)[1];
        int file3ID = headerPackets.get(2)[1];

        System.out.println(file1ID);
        System.out.println(file2ID);
        System.out.println(file3ID);

        for(int i = 0; i < heap.size(); i++) {
            byte[] packet = heap.get(i);
            int packetID = packet[1];

            if (packetID == file1ID) {
                file1.add(packet);

            } else if (packetID == file2ID) {
                file2.add(packet);

            } else {
                file3.add(packet);
            }

        }
        System.out.println();
//        for (int i = 0; i < file2.size(); i++) {
//            int packetID = file2.get(i)[1];
//            System.out.println(packetID);
//        }
        System.out.println("1: " + file1.size());
        System.out.println("2: " + file2.size());
        System.out.println("3: " + file3.size());
        System.out.println();

    }
}

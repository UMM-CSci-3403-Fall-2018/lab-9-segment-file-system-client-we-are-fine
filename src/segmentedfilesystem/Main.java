package segmentedfilesystem;

import java.util.ArrayList;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;


public class Main {

    static ArrayList<byte[]> heap = new ArrayList<byte[]>();
    static ArrayList<byte[]> lastPackets = new ArrayList<byte[]>();
    static ArrayList<byte[]> headerPackets = new ArrayList<byte[]>();

    public static void main(String[] args) {
        try {
            byte[] buf = new byte[1028];
            int port = 6014;
            InetAddress address = InetAddress.getByName("heartofgold.morris.umn.edu");
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);


            // Sending the Datagram
            socket.send(packet);

            // Getting a response back
            while ((lastPackets.size() < 3) || (heap.size() < totalPackets(lastPackets))) {

                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                byte[] received;
                received = packet.getData();

                checkPacketType(received);


            }
            System.out.println(lastPackets.size());
            System.out.println(headerPackets.size());
            System.out.println(heap.size());


        } catch (SocketException se) {

            System.out.println(se);

        } catch (UnknownHostException uhe) {

            System.out.println(uhe);

        } catch (IOException ioe) {

            System.out.println(ioe);
        }
    }

    public static int totalPackets(ArrayList<byte[]> lastPackets) {
        int totalPackets = 0;

        for (int i = 0; i < lastPackets.size(); i++) {
            totalPackets += getPacketNumber(lastPackets.get(i));
        }

        System.out.println("Total Packets: " + (totalPackets + lastPackets.size()));

        return totalPackets;
    }


    public static int getPacketNumber(byte[] received) {

        int packetByte1 = received[2];
        int packetByte2 = received[3];

        if (packetByte2 < 0) {
            packetByte2 += 256;
        }

        int packetNumber = 256 * packetByte1 + packetByte2;

        System.out.println("packet number: " + packetNumber);

        return packetNumber + 1; // The packet count starts at 0, so 1 is added
    }


    public static void checkPacketType(byte[] received) {

        if (received[0] % 2 == 1) {

            if (received[0] % 4 == 3) {
                lastPackets.add(received);
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

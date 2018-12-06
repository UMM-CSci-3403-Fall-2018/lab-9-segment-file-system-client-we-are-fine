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

    public static void main(String[] args) {

        try {
            PacketProcessor processor = new PacketProcessor();

            byte[] buf = new byte[1028];
            int port = 6014;
            InetAddress address = InetAddress.getByName("heartofgold.morris.umn.edu");
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);


            // Sending the Datagram
            socket.send(packet);

            // Getting a response back
            byte[] received;
//            byte[] copy;
            while ((processor.lastPackets.size() < 3) || (processor.heap.size() < processor.numPackets)) {

                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                received = packet.getData();

                byte[] copy;
                copy = Arrays.copyOf(received, received.length);

                processor.checkPacketType(copy);


            }
            System.out.println(processor.lastPackets.size());
            System.out.println(processor.headerPackets.size());
            System.out.println(processor.heap.size());

            processor.partitionFiles();
            processor.sortFiles();


        } catch (SocketException se) {

            System.out.println(se);

        } catch (UnknownHostException uhe) {

            System.out.println(uhe);

        } catch (IOException ioe) {

            System.out.println(ioe);
        }
    }
}

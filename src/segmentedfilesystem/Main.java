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
            Client client = new Client();

            byte[] buf = new byte[1028];
            int port = 6014;
            InetAddress address = InetAddress.getByName("heartofgold.morris.umn.edu");
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);


            // Sending the Datagram
            socket.send(packet);

            // Getting a response back
            while ((client.lastPackets.size() < 3) || (client.heap.size() < client.numPackets)) {

                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                byte[] received;
                received = packet.getData();

                client.checkPacketType(received);


            }
            System.out.println(client.lastPackets.size());
            System.out.println(client.headerPackets.size());
            System.out.println(client.heap.size());

            client.partitionFiles();


        } catch (SocketException se) {

            System.out.println(se);

        } catch (UnknownHostException uhe) {

            System.out.println(uhe);

        } catch (IOException ioe) {

            System.out.println(ioe);
        }
    }
}

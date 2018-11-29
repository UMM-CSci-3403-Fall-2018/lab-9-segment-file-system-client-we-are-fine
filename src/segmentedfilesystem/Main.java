package segmentedfilesystem;

import java.util.ArrayList;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {
    
    public static void main(String[] args) {
        try {
            byte[] buf = new byte[256];
            int port = 6014;
            InetAddress address = InetAddress.getByName("heartofgold.morris.umn.edu");
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);

            ArrayList heap = new ArrayList();

            // Sending the Datagram
            socket.send(packet);

            // Getting a response back
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            //DatagramPacket received = new (packet.getData(), 0, packet.getLength());
            byte[] received = new byte[packet.getLength()];
            received = packet.getData();

            heap.add(received);

        } catch(SocketException se) {

            System.out.println(se);

        } catch(UnknownHostException uhe) {

            System.out.println(uhe);

        } catch(IOException ioe) {

            System.out.println(ioe);
        }
    }

}

package segmentedfilesystem;

import Java.util.ArrayList;
import Java.net.DatagramPacket;
import Java.net.DatagramSocket;

public class Main {
    
    public static void main(String[] args) {
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

    }

}

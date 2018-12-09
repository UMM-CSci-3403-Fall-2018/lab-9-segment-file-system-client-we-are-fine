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
            System.out.println("Receiving Packets...");
            while ((processor.lastPackets.size() < 3) || (processor.heap.size() < processor.numPackets)) {

                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                byte[] received = new byte[packet.getLength()];
                System.arraycopy(packet.getData(), packet.getOffset(), received, 0, packet.getLength());

                processor.checkPacketType(received);


            }
            System.out.println("Assembling files...");

            processor.partitionFiles();
            processor.sortFiles();
            processor.writeFiles();

            System.out.println("done.");
//            for(int i = 0; i<processor.file1.size(); i++){
//                for(int j = 4; j<processor.file1.get(i).length; j++){
//                    System.out.write(processor.file1.get(i)[j]);
//                }
//            }


        } catch (SocketException se) {

            System.out.println(se);

        } catch (UnknownHostException uhe) {

            System.out.println(uhe);

        } catch (IOException ioe) {

            System.out.println(ioe);
        }
    }
}

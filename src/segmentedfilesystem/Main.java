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
            byte[] buf = new byte[1028];
            int port = 6014;
            InetAddress address = InetAddress.getByName("heartofgold.morris.umn.edu");
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);

            ArrayList<byte[]> heap = new ArrayList<byte[]>();
            ArrayList<byte[]> lastPackets = new ArrayList<byte[]>();

            // Sending the Datagram
            socket.send(packet);

            // Getting a response back
           //while (lastPackets.size() < 3 && heap.size() < totalPackets(lastPackets)) {

               packet = new DatagramPacket(buf, buf.length);
               socket.receive(packet);
               byte[] received = new byte[packet.getLength()];
               received = packet.getData();
               heap.add(received);

               // if(received[0] mod)

               getPacketNumber(received);

           //}

        } catch(SocketException se) {

            System.out.println(se);

        } catch(UnknownHostException uhe) {

            System.out.println(uhe);

        } catch(IOException ioe) {

            System.out.println(ioe);
        }
    }

    public static int totalPackets(ArrayList lastPackets){
        int totalPackets = 0;

//        for(int i = 0; i < lastPackets.size(); i++) {
//            totalPackets += getPacketNumber(lastPackets.get(i));
//        }

        return totalPackets + 6; //adding 6 since packet numbers start from 0 and don't include the header packets
    }


    public static int getPacketNumber(byte[] b){

        if (b[3] < 0) {
            b[3] += 256;
        }
        int packetNumber = 256*b[2]+b[3];

        System.out.println(packetNumber);

        return packetNumber;
    }


}

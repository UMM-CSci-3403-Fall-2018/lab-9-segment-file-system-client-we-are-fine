package segmentedfilesystem;

import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;

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
            headerPackets.add(received);
        }
    }

    public void partitionFiles() {
        int file1ID = headerPackets.get(0)[1];
        int file2ID = headerPackets.get(1)[1];

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
    }

    public void writeFiles() throws IOException {

        for(int k = 0; k<headerPackets.size(); k++) {
            byte[] header = headerPackets.get(k);

            byte[] byteFileName = new byte[header.length - 2];
            for (int i = 0; i < byteFileName.length; i++) {
                byteFileName[i] = header[i + 2];
            }

            String fileName = new String(byteFileName);
            System.out.println(fileName);

            ArrayList<byte[]> file = file1;
            if(k == 1) {
                file = file2;
            } else if (k == 2) {
                file = file3;
            }

            FileOutputStream out = new FileOutputStream(fileName);
            for (int i = 0; i < file.size(); i++) {
                byte[] fileData = file.get(i);
                for (int j = 4; j < fileData.length; j++) {
                    out.write(fileData[j]);
                }
            }
            out.close();
        }
    }



    public void sortFiles() {

        PacketComparator comparator = new PacketComparator();

        Collections.sort(file1, comparator);
        Collections.sort(file2, comparator);
        Collections.sort(file3, comparator);

    }

    class PacketComparator implements Comparator<byte[]> {

        public int compare(byte[] packet1, byte[] packet2) {
            return getPacketNumber(packet1) - (getPacketNumber(packet2));
        }

    }

}

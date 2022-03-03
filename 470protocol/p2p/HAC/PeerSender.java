package HAC;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PeerSender extends Thread{

    private ArrayList<String> peerList;
    private static DatagramSocket socket;
    private int status;
    public PeerSender(ArrayList<String> peerList)
    {
        this.peerList = peerList;
        this.status = 1;
    }

            @Override
            public void run() {
            try 
            {
                while (true)
                {
                    for (int peerNumber = 0; peerNumber < peerList.size(); peerNumber++)
                    {
                        socket = new DatagramSocket();
                        InetAddress destinationIP = InetAddress.getByName(peerList.get(peerNumber));
                        String status = "I'm alive! ";

                        ByteArrayOutputStream bytePacket = new ByteArrayOutputStream();
                        DataOutputStream outPacket = new DataOutputStream(bytePacket);
                        outPacket.writeBytes(status);
                        outPacket.writeBytes("v1.0 ");
                        outPacket.writeInt(1024);
                        outPacket.writeBytes("flag ");
                        outPacket.writeBytes("reserved");
                        outPacket.flush();
                        byte[] data = bytePacket.toByteArray();
                        
                        DatagramPacket packet = new DatagramPacket(data, data.length, destinationIP, 6969);
                        socket.send(packet);

                        System.out.println("Message sent from peer " + InetAddress.getLocalHost());
                        Thread.sleep(2000);

                        DatagramPacket inPacket = new DatagramPacket(data, data.length);
                        socket.receive(inPacket);
                        String response = new String(inPacket.getData());
                        System.out.println("Response from peer: " + response);
                    }
                }
            } 
            catch (UnknownHostException e) 
            {
                e.printStackTrace();
            } 
            catch (SocketException e) 
            {
                e.printStackTrace();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }

    public int checkStatus()
    {
        return status;
    }
}

package HAC;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class PeerReceiver extends Thread {
    DatagramSocket socket;
    DatagramPacket packet;
    ArrayList<String> peerList;
    private int status;

    public PeerReceiver(ArrayList<String> peerList)
    {
        this.peerList = peerList;
    }

            @Override
            public void run() {
        try 
        {
            socket = new DatagramSocket(6969);
            byte[] incomingData = new byte[1024];
    
            while (true) 
            {
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, 
                        incomingData.length);
                socket.receive(incomingPacket);
                String message = new String(incomingPacket.getData());
                InetAddress senderIP = incomingPacket.getAddress();
                int port = incomingPacket.getPort();
                
                System.out.println("Received message from client: " + message);
                System.out.println("Client IP:" + senderIP.getHostAddress());
                System.out.println("Client port:" + port);
                
                ByteArrayOutputStream bytePacket = new ByteArrayOutputStream();
                DataOutputStream outPacket = new DataOutputStream(bytePacket);
                String reply = "received ";
                outPacket.writeBytes(reply);
                outPacket.writeBytes("v1.0 ");
                outPacket.writeInt(1024);
                outPacket.writeBytes("flag ");
                outPacket.writeBytes("reserved");
                outPacket.flush();
                byte[] data = bytePacket.toByteArray();
                
                DatagramPacket replyPacket =
                        new DatagramPacket(data, data.length, senderIP, port);
                
                socket.send(replyPacket);
                Thread.sleep(2000);
                //socket.close();
            }
        } 
        catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException i) 
        {
            i.printStackTrace();
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

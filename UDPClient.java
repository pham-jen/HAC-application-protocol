import java.io.IOException;
import java.net.*;
import java.lang.Thread;
import java.io.*;
public class UDPClient 
{
    DatagramSocket Socket;

    public UDPClient() 
    {
    }

    public void createAndListenSocket() 
    {
        try 
        {
            Socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] incomingData = new byte[1024];
            String sentence = "I'm alive!";
            byte[] data = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);

            //while (true)
            for (int i = 0; i < 5; i++)
            {
                Socket.send(sendPacket);
                Thread.sleep(1000);
                System.out.println("Message sent from client");
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                Socket.receive(incomingPacket);
                String response = new String(incomingPacket.getData());
                System.out.println("Response from server: " + response);
                Socket.close();
            }

            /*System.out.println("Message sent from client");
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            Socket.receive(incomingPacket);
            String response = new String(incomingPacket.getData());
            System.out.println("Response from server: " + response);
            Socket.close();*/
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
        catch (Exception e) {
           
            // catching the exception
            System.out.println(e);
        }
    }

    /*public void acknowledgement()
    {
        while (true)
        {
            Socket.send(p);
        }
    }*/

    public static void main(String[] args) 
    {
        UDPClient client = new UDPClient();
        client.createAndListenSocket();
    }
}
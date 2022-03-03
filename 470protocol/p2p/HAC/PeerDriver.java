package HAC;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class PeerDriver
{
    private static ArrayList<String> peerList = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException
    {
        try 
        {
            File peerAddresses = new File("p2p/HAC/peerAddresses.txt");
            Scanner scanner = new Scanner(peerAddresses);
            while (scanner.hasNextLine()) 
            {
                peerList.add(scanner.nextLine());
            }
            scanner.close();
            System.out.println("File read, starting P2P");
            
            
            PeerSender sender = new PeerSender(peerList);
            PeerReceiver receiver = new PeerReceiver(peerList);
            receiver.start();
            sender.start();
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
    }
}
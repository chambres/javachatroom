import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.*;

public class ClientMain
{
    public static void main(String[] args)
    {
        GameData gameData = new GameData();


        Scanner keyboard = new Scanner(System.in);
        //System.out.print("Enter the ip address of the server: ");
        String ip = "127.0.0.1";//keyboard.next();

        

        try {
            Socket socket = new Socket(ip,8002);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


            ClientListenerForNames tmpClientListener = new ClientListenerForNames(ois);
            Thread tmp = new Thread(tmpClientListener);
            tmp.start();        


            System.out.println(tmpClientListener.getNames().toString());
            


            String name = "";
            while(true){
                System.out.println("Enter your name: ");
                name = keyboard.nextLine();
                if(tmpClientListener.getNames().contains(name)){
                    System.out.println("Name already taken");
                    continue;
                }
                else{
                    break;
                }
            }

            
            System.out.println("Name chosen: " + name);
            oos.writeObject(new CommandFromClient(CommandFromClient.SENDNAME, name));

            
            Rolodex frame = new Rolodex(tmpClientListener.getNames());
            System.out.println(frame);
            Thread t = new Thread(new ClientListener(ois,frame));
            t.start();
            frame.setVisible(true);

            //System.exit(0);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

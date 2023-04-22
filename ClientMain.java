import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ClientMain
{
    public static void main(String[] args)
    {
        GameData gameData = new GameData();


        Scanner keyboard = new Scanner(System.in);
        //System.out.print("Enter the ip address of the server: ");
        //String ip = keyboard.nextLine();
        String ip = "127.0.0.1";

        try {

                Socket socket = new Socket(ip,8002);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());   
                
                
                oos.writeObject(new CommandFromClient(CommandFromClient.REQUESTNAMES,""));

                String names = "";
                while(true) {
                    CommandFromServer com = (CommandFromServer) (ois.readObject());
                    if(com.getCommand() == CommandFromServer.SENDNAMES){
                        System.out.println(com.getData());
                        names = com.getData();
                        break;
                    }
                }

                String[] namesAlreadyInUse = names.split(",");
                while(true){
                    System.out.print("Enter your name: ");
                    String name = keyboard.nextLine();
                    
                    if(Arrays.asList(namesAlreadyInUse).contains(name)){
                        System.out.println("Name already taken");
                        continue;
                    }
                    else{
                        oos.writeObject(new CommandFromClient(CommandFromClient.NEWNAME,name));
                        
                    Rolodex frame = new Rolodex("tmp", oos, name);
                    Thread t = new Thread(new ClientListener(ois,frame));
                    t.start();
                    frame.setVisible(true);
                        break;
                    }

                }


            }


        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

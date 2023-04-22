import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain
{

    public static void addName(String name){
        names.add(name);
    }
    public static ArrayList<String> getnames(){
        return names;
    }

    public static ArrayList<String> names = new ArrayList<String>();

    public static void main(String[] args) {
        try
        {
            
            GameData gameData = new GameData();
            ServerSocket socketSocket = new ServerSocket(8002);


            while(true){
                Socket socketToX = socketSocket.accept();
                ObjectInputStream oisX = new ObjectInputStream(socketToX.getInputStream());
                ObjectOutputStream oosX = new ObjectOutputStream(socketToX.getOutputStream());
                System.out.println("someone has connected");
                Thread t = new Thread(new ServersListener(gameData,oisX,oosX,'x'));
                t.start();
                oosX.writeObject(new CommandFromServer(CommandFromServer.CONNECTED_AS_X,""));

            }



        }
        catch (Exception e)
        {}
    }
}

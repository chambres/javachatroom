import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain
{
    public static ArrayList<String> names = new ArrayList<>();
    
    public static void main(String[] args) {
        try
        {

            GameData gameData = new GameData();
            ServerSocket socketSocket = new ServerSocket(8002);


            while(true){
                Socket user = socketSocket.accept();
                ObjectInputStream oisX = new ObjectInputStream(user.getInputStream());
                ObjectOutputStream oosX = new ObjectOutputStream(user.getOutputStream());
                System.out.println("x has connected");
                oosX.writeObject(new CommandFromServer(CommandFromServer.RECIEVENAMES, names.toString()));

                Thread t = new Thread(new ServersListener(gameData,oisX,oosX,'x'));

                t.start();
                oosX.writeObject(new CommandFromServer(CommandFromServer.CONNECTED_AS_X,""));
            }

        }
        catch (Exception e)
        {}
    }
}

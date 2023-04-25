import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ServersListener implements Runnable{
    private ObjectInputStream ois =null;
    private ObjectOutputStream oos =null;
    private static ArrayList<ObjectOutputStream> outputStreams = new ArrayList<>();
    private char player = 'x';
    private static char turn = 'x';
    private GameData gameData;
    private static boolean xRestart, oRestart;

    boolean xConnected, yConnected;

    public ServersListener(GameData gameData,ObjectInputStream ois, ObjectOutputStream oos, char player) {
        this.ois = ois;
        this.oos = oos;
        this.gameData  =gameData;
        this.player = player;
        outputStreams.add(oos);
    }

    public void run()
    {
        try
        {
            while(true) {
                CommandFromClient com = (CommandFromClient) (ois.readObject());

                if(com.getCommand() == CommandFromClient.MESSAGE){
                    for (ObjectOutputStream os : outputStreams){
                        try{
                            os.writeObject(new CommandFromServer(CommandFromServer.MESSAGE, com.getData()));
                        }
                        catch(Exception e){
                            System.out.println(e);
                            continue;
                        }
                    }                        
                }

                if(com.getCommand() == CommandFromClient.REQUESTNAMES){
                    for (ObjectOutputStream os : outputStreams){
                        try{
                            os.writeObject(new CommandFromServer(CommandFromServer.SENDNAMES, String.join(";", ServerMain.getnames())));
                        }
                        catch(Exception e){
                            System.out.println(e);
                            continue;
                        }
                    }
                }

                if(com.getCommand() == CommandFromClient.NEWNAME){
                    ServerMain.addName(com.getData());

                    for (ObjectOutputStream os : outputStreams){
                        try{
                            os.writeObject(new CommandFromServer(CommandFromServer.SENDNAMES, ServerMain.getnames().toString()));
                        }
                        catch(Exception e){
                            System.out.println(e);
                            continue;
                        }
                    }
                }

                if(com.getCommand() == CommandFromClient.LOGOUT){
                    ServerMain.removeName(com.getData());
                    for (ObjectOutputStream os : outputStreams){
                        try{
                        os.writeObject(new CommandFromServer(CommandFromServer.SENDNAMES, ServerMain.getnames().toString()));
                        }
                        catch(Exception e){
                            System.out.println(e);
                            continue;
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            System.out.println("Disconnection");
            

            //System.exit(0);
        }
    }
}

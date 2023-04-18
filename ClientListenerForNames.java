import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ClientListenerForNames implements Runnable{
    private ObjectInputStream ois =null;


    public ClientListenerForNames(ObjectInputStream ois) {
        this.ois = ois;
    }

    public String getNames(){
        return names;
    }

    String names = "";


    public void run()
    {
        try
        {
            while(true) {
                CommandFromServer com = (CommandFromServer) (ois.readObject());

                if (com.getCommand() == CommandFromServer.RECIEVENAMES) {
                    names = com.getData();
                    //System.out.println("got names: " + com.getData());
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Error");
            System.exit(0);
        }
    }
}

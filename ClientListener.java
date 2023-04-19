import java.io.ObjectInputStream;

public class ClientListener implements Runnable{
    private ObjectInputStream ois =null;    
    private Rolodex frame = null;

    public ClientListener(ObjectInputStream ois, Rolodex frame) {
        this.ois = ois;
        this.frame = frame;
    }

    public void run()
    {
        try
        {
            while(true) {
                CommandFromServer com = (CommandFromServer) (ois.readObject());

                if (com.getCommand() == CommandFromServer.CONNECTED_AS_X) {
                    frame.setText("Waiting for RED to connect");
                }
                if (com.getCommand() == CommandFromServer.CONNECTED_AS_O) {
                    frame.setText("Blue's Turn");

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

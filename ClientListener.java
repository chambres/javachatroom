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

                if(com.getCommand() == CommandFromServer.MESSAGE){
                    frame.setText(com.getData());
                    frame.reloadButtons();
                }
                if(com.getCommand() == CommandFromServer.SENDNAMES){
                    frame.setNames(com.getData());
                }

                

            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            //System.exit(0);
        }
    }
}

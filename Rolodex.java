import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class Rolodex extends JFrame {


    JTextField firstNameField = new JTextField();
    JTextField lastNameField = new JTextField();
    JTextField phoneNumberField = new JTextField();
    JTextField addressField = new JTextField();

    JButton submit;
    JButton clear;
    JButton saveChanges;
    JButton deleteContact;

    
    static ArrayList<JLabel> contactList = new ArrayList<JLabel>();

    JScrollPane scrollPane;


    int maxPanelWidth = 450; // set the maximum width of the panel

    private JPanel buttonPanel = new JPanel(new GridLayout(0, 1)); // 1 column grid
    
    String myname;

    public Rolodex(String names, ObjectOutputStream oos, String myname) {
        super("Chat Room | Signed in as " + myname);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);

        Start();
        reloadButtons();

        this.oos = oos;
        this.myname = myname;

        //System.out.println(names);
        
        setNames(names);



        setVisible(true);
    }

    ObjectOutputStream oos;


    JTextArea field;
    JTextArea users;

    WindowListener exitListener = new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showOptionDialog(
                 null, "Are You Sure to Close Application?", 
                 "Exit Confirmation", JOptionPane.YES_NO_OPTION, 
                 JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == 0) {
                try {
                    oos.writeObject(new CommandFromClient(CommandFromClient.LOGOUT,myname));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
               System.exit(0);
            }
        }
    };


    void Start(){

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.PAGE_START);
        scrollPane = new JScrollPane(panel);
        scrollPane.getViewport().setPreferredSize(new Dimension(450, 500));
        
        
        JPanel topPanel = new JPanel();
        
        int gap = 2;
        //setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        //setLayout(new BorderLayout(gap, gap));
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(850, 500));
        centerPanel.setMaximumSize(new Dimension(450, 500));
        centerPanel.add(scrollPane, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(null);        
        rightPanel.setPreferredSize(new Dimension(400, 500));
        rightPanel.setMaximumSize(new Dimension(400, 500));
        rightPanel.setBackground(Color.red);

        JLabel firstNameLabel = new JLabel("Chat Box: ");
        int labelX = 40; // set the X position of the labels
        int labelY = 30; // set the Y position of the first label
        int labelHeight = 20; // set the height of the labels
        int labelGap = 10; // set the vertical gap between the labels

        JLabel[] labels = {firstNameLabel}; // store the labels in an array
        for(JLabel label : labels) { // loop through the labels
            label.setBounds(labelX, labelY, 100, labelHeight); // hardcode the position and size of the label
            labelY += labelHeight + labelGap; // update the Y position for the next label
            rightPanel.add(label);
        }

        labelY = 30;
        field = new JTextArea(5, 20);
        field.setLineWrap(true);
        field.setBounds(130, labelY, 220, 100); // hardcode the position and size of the text field



        users = new JTextArea(5, 20);
        users.setLineWrap(true);
        users.setBounds(130, labelY+100+20+200, 220, 100); // hardcode the position and size of the text field
        users.setEditable(false);

        JLabel usersLabel = new JLabel("Users: ");
        usersLabel.setBounds(60, labelY+100+20+160, 220, 100);
        rightPanel.add(usersLabel);
        rightPanel.add(users);
        
        rightPanel.add(field);

        submit = new JButton("Submit");
        submit.setBounds(180-60+60-30, 200+20 + 20, 100, 20);
        rightPanel.add(submit);
        submit.addActionListener(submitButtonListener());


        JButton test = new JButton("Test");
        test.setBounds(180-60+60-30, 200+20+70+70+00, 100, 20);
        test.addActionListener(testListener());
        rightPanel.add(test);

    
        centerPanel.add(rightPanel, BorderLayout.EAST);
        
        
        add(topPanel, BorderLayout.PAGE_START);
        add(centerPanel, BorderLayout.CENTER);

        addWindowListener(exitListener);

        setVisible(true);
        
    }

    ActionListener testListener(){
        
        return new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Test");
                 for (int i = 1; i <= 21; i++) { // add some sample buttons
                    JButton button = new JButton("Button " + i);
                    button.setAlignmentX(Component.CENTER_ALIGNMENT); // set the alignment of the button to center
                    button.setHorizontalAlignment(SwingConstants.LEFT); // left align the text on the button
                    button.setMaximumSize(new Dimension(maxPanelWidth, button.getPreferredSize().height)); // set the maximum size of the button to the maximum width of the panel and the preferred height of the button
                    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5))); // add a 5-pixel vertical gap between buttons
                    contactList.add(new JLabel("a"));
                    
                    reloadButtons();

                    buttonPanel.revalidate(); // tell the panel to update its layout
                    
                    scrollPane.setViewportView(buttonPanel);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    scrollPane.revalidate();
                    setVisible(true);
                }
            }
        };
    }


    public void setText(String a){
        contactList.add(new JLabel(a));
        reloadButtons();
    }

    public void setNames(String names){
        names = names.substring(1, names.length() - 1);
        names = names.replaceAll(", ", "\n");
        users.setText(names);
        revalidate();
    }

    

    void addButton(){
        String fname = myname +  ": " + field.getText();
        try {
            oos.writeObject(new CommandFromClient(CommandFromClient.MESSAGE, fname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void reloadButtons() {
        buttonPanel.removeAll(); // remove all existing buttons from the panel

        for (JLabel button : contactList) {
            button.setAlignmentX(Component.LEFT_ALIGNMENT); // set the alignment of the button to center
            button.setHorizontalAlignment(SwingConstants.LEFT); // left align the text on the button
            button.setMaximumSize(new Dimension(maxPanelWidth, button.getPreferredSize().height)); // set the maximum size of the button to the maximum width of the panel and the preferred height of the button
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.add(button);
            // row.add(label);
            // row.add(txtFld);
            buttonPanel.add(row);
            scrollPane.revalidate();
        }
        // tell the panel to update its layout
        setVisible(true);
    }
    

    ActionListener submitButtonListener(){

    return new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            

            addButton();
            field.setText("");

        }
        };
    }
    
       


       

    public static void main(String[] args) {
        
    }
}
//In this version of the AddNewProject2 class, I changed the layout manager of the centerPanel to a BorderLayout, and then added the scrollPane to the BorderLayout.WEST position of the centerPanel. This will cause the scrollPane and its contents to be locked to the left side of the centerPanel.

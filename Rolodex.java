import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Rolodex extends JPanel {


    JTextField firstNameField = new JTextField();
    JTextField lastNameField = new JTextField();
    JTextField phoneNumberField = new JTextField();
    JTextField addressField = new JTextField();

    JButton submit;
    JButton clear;
    JButton saveChanges;
    JButton deleteContact;

    ActionListener b = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Button clicked");
            ContactButton tmp = (ContactButton) e.getSource();
            firstNameField.setText(tmp.fname);
            lastNameField.setText(tmp.lname);
            phoneNumberField.setText(tmp.phone);
            addressField.setText(tmp.add);
            
            saveChanges.setEnabled(true);
            deleteContact.setEnabled(true);
            submit.setEnabled(false);
            clear.setEnabled(false);

            current = tmp;
        }
    };

    static ArrayList<JLabel> contactList = new ArrayList<JLabel>();

    ContactButton current = null;

    JScrollPane scrollPane;


    int maxPanelWidth = 450; // set the maximum width of the panel

    private JPanel buttonPanel = new JPanel(new GridLayout(0, 1)); // 1 column grid

    public Rolodex() {
    

        Start();
        reloadButtons();
        
            

        


        


    }

    JTextArea field;
    ArrayList<String> users = new ArrayList<String>();

    void Start(){

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.PAGE_START);
        scrollPane = new JScrollPane(panel);
        scrollPane.getViewport().setPreferredSize(new Dimension(450, 500));
        
        
        JPanel topPanel = new JPanel();
        
        int gap = 2;
        setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        setLayout(new BorderLayout(gap, gap));
        
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



        JTextArea users = new JTextArea(5, 20);
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




    void addButton(){
        String fname = field.getText();
        contactList.add(new JLabel(fname));
        reloadButtons();
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
        SwingUtilities.invokeLater(() -> {

            Scanner scan = new Scanner(System.in);
            //System.out.println("Enter IP: ");
            String ip = "127.0.0.1";


            String name = scan.nextLine();



            JFrame f = new JFrame("GUI");

            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                WindowListener listener = new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent we) {
                        
                            f.setVisible(false);
                            f.dispose();
                        
                    }
                };
                f.addWindowListener(listener);
            
            Rolodex project2 = new Rolodex();
            f.add(project2);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
//In this version of the AddNewProject2 class, I changed the layout manager of the centerPanel to a BorderLayout, and then added the scrollPane to the BorderLayout.WEST position of the centerPanel. This will cause the scrollPane and its contents to be locked to the left side of the centerPanel.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
public class ContactButton extends JButton{
    
    String fname;
    String lname;
    String phone;
    String add;
    int index;

    //create constructor
    public ContactButton(String fname, String lname, String phone, String add){
        super(lname +", " + fname);
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.add = add;
        
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    @Override
    public String toString(){
        return fname + "," + lname + "," + phone + "," + add;
    }
    
}
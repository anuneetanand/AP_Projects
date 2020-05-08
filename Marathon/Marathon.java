//Anuneet Anand
//2018022
//CSE

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

class Runner
{
    private String Name;
    private int Time;
    private String Category;
    private Runner Next;
    //Constructor
    public Runner(String Name, int Time, String Category)
    {
        this.Name=Name;
        this.Time=Time;
        this.Category=Category;
    }
    //Getters & Setters
    public Runner getNext()
    {   return Next;    }
    public void setNext(Runner next)
    {   Next = next;    }
    public String getName()
    {   return Name;    }
    public int getTime()
    {   return Time;    }
    public String getCategory()
    {   return Category;    }
}

class RunnerList
{
    private Runner Head;
    private int Length;
    private Runner A1,A2;
    private Runner B1,B2;
    private Runner C1,C2;
    //Constructor
    public RunnerList()
    {
        Head=null;
        A1=null;A2=null;
        B1=null;B2=null;
        C1=null;C2=null;
        Length=0;
    }
    //To Add New Node
    public void Insert(Runner R)
    {
        if(Head==null)
        {   Head=R; }
        else
        {
            Runner X=Head;
            while (X.getNext()!=null)
            {   X=X.getNext();  }
            X.setNext(R);
        }
        Length++;
    }
    //To Find Fastest Runners In Each Category
    public void Rank()
    {
        Runner X=Head;
        while(X!=null)
        {
            if (X.getCategory().equals("Half Marathon (20 Km)"))
            {
                if (A1==null)
                    A1=X;
                else if (A1.getTime()>X.getTime())
                    A1=X;
            }
            if (X.getCategory().equals("Open 10K Run (10Km)"))
            {
                if (B1==null)
                    B1=X;
                else if (B1.getTime()>X.getTime())
                    B1=X;
            }
            if (X.getCategory().equals("Great Delhi Run (5Km)"))
            {
                if (C1==null)
                    C1=X;
                else if (C1.getTime()>X.getTime())
                    C1=X;
            }
            X=X.getNext();
        }

        X=Head;
        while(X!=null)
        {
            if (X.getCategory().equals("Half Marathon (20 Km)"))
            {
                if (A1!=null && A1!=X)
                    if(A2==null)
                        A2=X;
                    else if (A2.getTime()>X.getTime())
                        A2=X;
            }
            if (X.getCategory().equals("Open 10K Run (10Km)"))
            {
                if(B1!=null && B1!=X)
                    if (B2==null)
                        B2=X;
                    else if (B2.getTime()>X.getTime())
                        B2=X;
            }
            if (X.getCategory().equals("Great Delhi Run (5Km)"))
            {
                if(C1!=null && C1!=X)
                    if (C2==null)
                        C2=X;
                    else if (C2.getTime()>X.getTime())
                        C2=X;
            }
            X=X.getNext();
        }
    }
    //Getters & Setters
    public Runner getHead()
    {   return Head;    }
    public int getLength()
    {   return Length;  }
    public Runner getA1()
    {   return A1;  }
    public Runner getA2()
    {   return A2;  }
    public Runner getB1()
    {   return B1;  }
    public Runner getB2()
    {   return B2;  }
    public Runner getC1()
    {   return C1;  }
    public Runner getC2()
    {   return C2;  }
}

public class Marathon
{
    public static void main(String[] args)
    {
        RunnerList RL = new RunnerList();

        // Main Frame
        JFrame frame = new JFrame("MARATHON");
        JPanel p_main = new JPanel();
        p_main.setLayout(new BoxLayout(p_main, BoxLayout.Y_AXIS));

        //Error Frame
        JFrame frame_e = new JFrame("ERROR!");
        JPanel p_error = new JPanel();
        p_error.setLayout(new BoxLayout(p_error, BoxLayout.Y_AXIS));
        JPanel p_message=new JPanel();
        p_message.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton b_ok = new JButton("OK");
        JLabel l_error = new JLabel("Please Enter All Fields!");
        p_message.add(l_error);
        p_error.add(p_message);
        p_error.add(b_ok);
        frame_e.add(p_error);

        // Name
        JPanel p_name = new JPanel();
        p_name.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_name = new JLabel("Name: ");
        JTextField tf_name= new JTextField();
        tf_name.setPreferredSize(new Dimension(200,30));
        p_name.add(l_name);
        p_name.add(tf_name);
        p_main.add(p_name);

        // Time
        JPanel p_time = new JPanel();
        p_time.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_time = new JLabel("Time: ");
        JTextField tf_time= new JTextField();
        tf_time.setPreferredSize(new Dimension(100,30));
        p_time.add(l_time);
        p_time.add(tf_time);
        p_main.add(p_time);

        // Category
        JPanel p_category = new JPanel();
        p_time.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_category = new JLabel("Choose Your Category: ");
        p_category.add(l_category);

        // Radio Buttons
        ButtonGroup bg_category = new ButtonGroup();
        JRadioButton rb_1 = new JRadioButton("Half Marathon (20 Km)");
        JRadioButton rb_2 = new JRadioButton("Open 10K Run (10Km)");
        JRadioButton rb_3 = new JRadioButton("Great Delhi Run (5Km)");

        bg_category.add(rb_1);
        bg_category.add(rb_2);
        bg_category.add(rb_3);

        p_category.add(rb_1);
        p_category.add(rb_2);
        p_category.add(rb_3);
        p_main.add(p_category);

        // Buttons
        JPanel p_buttons = new JPanel();
        p_buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton b_next = new JButton("Next");
        JButton b_winner = new JButton("Winner");
        JButton b_cancel = new JButton("Cancel");
        p_buttons.add(b_next);
        p_buttons.add(b_winner);
        p_buttons.add(b_cancel);
        p_main.add(p_buttons);

        //Category 1
        JPanel p_message1 = new JPanel();
        p_message1.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_message1 = new JLabel("                                    Half Marathon (20 Km)");
        p_message1.add(l_message1);
        p_main.add(p_message1);

        JPanel p_first1 = new JPanel();
        p_first1.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_first1 = new JLabel("FIRST                        ");
        JLabel l_first1n = new JLabel("Name:");
        JTextField tf_first1n = new JTextField();
        tf_first1n.setPreferredSize(new Dimension(200,30));
        tf_first1n.setEditable(false);
        JLabel l_first1p = new JLabel("Prize:");
        JTextField tf_first1p = new JTextField();
        tf_first1p.setPreferredSize(new Dimension(150,30));
        tf_first1p.setEditable(false);
        p_first1.add(l_first1);
        p_first1.add(l_first1n);
        p_first1.add(tf_first1n);
        p_first1.add(l_first1p);
        p_first1.add(tf_first1p);
        p_main.add(p_first1);

        JPanel p_second1 = new JPanel();
        p_second1.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_second1 = new JLabel("SECOND                  ");
        JLabel l_second1n = new JLabel("Name:");
        JTextField tf_second1n = new JTextField();
        tf_second1n.setPreferredSize(new Dimension(200,30));
        tf_second1n.setEditable(false);
        JLabel l_second1p = new JLabel("Prize:");
        JTextField tf_second1p = new JTextField();
        tf_second1p.setPreferredSize(new Dimension(150,30));
        tf_second1p.setEditable(false);
        p_second1.add(l_second1);
        p_second1.add(l_second1n);
        p_second1.add(tf_second1n);
        p_second1.add(l_second1p);
        p_second1.add(tf_second1p);
        p_main.add(p_second1);

        //Category 2
        JPanel p_message2 = new JPanel();
        p_message2.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_message2 = new JLabel("                                    Open 10K Run (10Km)");
        p_message2.add(l_message2);
        p_main.add(p_message2);

        JPanel p_first2 = new JPanel();
        p_first2.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_first2 = new JLabel("FIRST                         ");
        JLabel l_first2n = new JLabel("Name:");
        JTextField tf_first2n = new JTextField();
        tf_first2n.setPreferredSize(new Dimension(200,30));
        tf_first2n.setEditable(false);
        JLabel l_first2p = new JLabel("Prize:");
        JTextField tf_first2p = new JTextField();
        tf_first2p.setPreferredSize(new Dimension(150,30));
        tf_first2p.setEditable(false);
        p_first2.add(l_first2);
        p_first2.add(l_first2n);
        p_first2.add(tf_first2n);
        p_first2.add(l_first2p);
        p_first2.add(tf_first2p);
        p_main.add(p_first2);

        JPanel p_second2 = new JPanel();
        p_second2.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_second2 = new JLabel("SECOND                  ");
        JLabel l_second2n = new JLabel("Name:");
        JTextField tf_second2n = new JTextField();
        tf_second2n.setPreferredSize(new Dimension(200,30));
        tf_second2n.setEditable(false);
        JLabel l_second2p = new JLabel("Prize:");
        JTextField tf_second2p = new JTextField();
        tf_second2p.setPreferredSize(new Dimension(150,30));
        tf_second2p.setEditable(false);
        p_second2.add(l_second2);
        p_second2.add(l_second2n);
        p_second2.add(tf_second2n);
        p_second2.add(l_second2p);
        p_second2.add(tf_second2p);
        p_main.add(p_second2);

        //Category 3
        JPanel p_message3 = new JPanel();
        p_message3.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_message3 = new JLabel("                                    Great Delhi Run (5Km)");
        p_message3.add(l_message3);
        p_main.add(p_message3);

        JPanel p_first3 = new JPanel();
        p_first3.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_first3 = new JLabel("FIRST                         ");
        JLabel l_first3n = new JLabel("Name:");
        JTextField tf_first3n = new JTextField();
        tf_first3n.setPreferredSize(new Dimension(200,30));
        tf_first3n.setEditable(false);
        JLabel l_first3p = new JLabel("Prize:");
        JTextField tf_first3p = new JTextField();
        tf_first3p.setPreferredSize(new Dimension(150,30));
        tf_first3p.setEditable(false);
        p_first3.add(l_first3);
        p_first3.add(l_first3n);
        p_first3.add(tf_first3n);
        p_first3.add(l_first3p);
        p_first3.add(tf_first3p);
        p_main.add(p_first3);

        JPanel p_second3 = new JPanel();
        p_second3.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel l_second3 = new JLabel("SECOND                  ");
        JLabel l_second3n = new JLabel("Name:");
        JTextField tf_second3n = new JTextField();
        tf_second3n.setPreferredSize(new Dimension(200,30));
        tf_second3n.setEditable(false);
        JLabel l_second3p = new JLabel("Prize:");
        JTextField tf_second3p = new JTextField();
        tf_second3p.setPreferredSize(new Dimension(150,30));
        tf_second3p.setEditable(false);
        p_second3.add(l_second3);
        p_second3.add(l_second3n);
        p_second3.add(tf_second3n);
        p_second3.add(l_second3p);
        p_second3.add(tf_second3p);
        p_main.add(p_second3);

        //Next Button
        b_next.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int f=1;
                int time=0;
                String name = tf_name.getText();
                String time_s=tf_time.getText();
                if (!(time_s.equals("")))
                { time = Integer.parseInt((time_s));}
                String category="";
                if (rb_1.isSelected())
                    category="Half Marathon (20 Km)";
                else if(rb_2.isSelected())
                    category="Open 10K Run (10Km)";
                else if(rb_3.isSelected())
                    category="Great Delhi Run (5Km)";
                else
                    f=0;
                if (f==1 && !(name.equals("")) && !(time_s.equals("")))
                {
                    Runner Z = new Runner(name, time, category);
                    RL.Insert(Z);
                    tf_name.setText("");
                    tf_time.setText("");
                    bg_category.clearSelection();
                }
                else
                {
                    frame_e.setSize(300, 100);
                    frame_e.setVisible(true);
                }
            }
        });
        //OK Button
        b_ok.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {   frame_e.setVisible(false); }
        });
        //Winner Button
        b_winner.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                RL.Rank();

                if (RL.getA1()!=null) {
                    tf_first1n.setText(     RL.getA1().getName());
                    tf_first1p.setText("    Rs.2,80,000");}
                if (RL.getA2()!=null){
                    tf_second1n.setText(    RL.getA2().getName());
                    tf_second1p.setText("   Rs.2,10,000");}
                if (RL.getB1()!=null){
                    tf_first2n.setText(     RL.getB1().getName());
                    tf_first2p.setText("    Rs.1,90,000");}
                if (RL.getB2()!=null){
                    tf_second2n.setText(    RL.getB2().getName());
                    tf_second2p.setText("   Rs.1,50,000");}
                if (RL.getC1()!=null){
                    tf_first3n.setText(     RL.getC1().getName());
                    tf_first3p.setText("    Rs.1,35,000");}
                if (RL.getC2()!=null){
                    tf_second3n.setText(    RL.getC2().getName());
                    tf_second3p.setText("   Rs.1,15,000");}
            }
        });
        //Cancel Button
        b_cancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {   System.exit(0); }
        });

        //////////////////////////////////////////////////////////////////
        frame.add(p_main);
        frame.setSize(1200, 1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

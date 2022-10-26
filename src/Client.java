import java.net.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.awt.BorderLayout;


import javax.swing.*;

public class Client{
    
    String name = "Server";
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;
    String s;
    ReadThread rt;
    WriteThread wt;
    
    //Declaring components
    JFrame frame = new JFrame();
    JLabel heading = new JLabel("Chat Area");
    JTextArea msg_Area = new JTextArea();
    JTextField msg_In = new JTextField();
    Font font = new Font("Lato",Font.PLAIN,15);
   
    public Client(){
        try {
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",7777);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            create_GUI();
            events();
            ReadThread rt = new ReadThread(name, reader, frame , msg_In , msg_Area);
            rt.start();
            WriteThread wt = new WriteThread(reader, writer);
            wt.start();
        } catch (Exception e){
           e.printStackTrace();
        }
    } // end of constructor.


     public void events(){
        msg_In.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) { 
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    String contentSend = msg_In.getText();
                    msg_Area.append(contentSend+"\n");
                    writer.println(contentSend);
                    writer.flush();
                    String reset = "";
                    msg_In.setText(reset);
                    msg_In.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e){

            }
        });
            
    } //end of events.

    public void create_GUI(){
        //gui code
        frame.setTitle("VChat[Client]");
        frame.setSize(500,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //coding for components
        heading.setFont(font);
        msg_Area.setFont(font);
        msg_Area.setEditable(false);
        msg_In.setFont(font);
        heading.setIcon(new ImageIcon("sms.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        //frame layout
        frame.setLayout(new BorderLayout());

        //adding components to frame.
        frame.add(heading,BorderLayout.NORTH);
        frame.add(msg_Area,BorderLayout.CENTER);
        frame.add(msg_In,BorderLayout.SOUTH);

        frame.setVisible(true);
    } // end of GUI.
}
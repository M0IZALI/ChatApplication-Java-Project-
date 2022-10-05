import java.net.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.awt.BorderLayout;

import javax.swing.*;

public class Client{
    
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;
    String s;
    
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
            System.out.println("Connection Established");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            //new userInterface();
            GUI();
            events();
            startReading();
            startWriting();
        } catch (Exception e){
           e.printStackTrace();
        }
    } // end of constructor.


     public void events(){
        msg_In.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                if(e.getKeyCode() == 10){
                    String contentSend = msg_In.getText();
                    msg_Area.append("Me: "+contentSend+"\n");
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
            
    }

    public void GUI(){
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
    }

    public void startReading(){
        //Thread -> for reading the data.
        Runnable r1 = ()->{
            System.out.println("reader started");

            while(true){
                try{
                String msg = reader.readLine();
                if(msg.equals("exit"))
                {System.out.println("Server terminated the chat");
                JOptionPane.showMessageDialog(frame, "Server ended the chat");
                msg_In.setEnabled(false);
                break;}
                //System.out.println("Server:"+msg);
                msg_Area.append("Server: "+msg+"\n");
              }
            catch(Exception e){
                e.printStackTrace();}
            }
        };
        new Thread(r1).start();}

    public void startWriting(){
        //Thread -> for sending the data.
        System.out.println("Writer started");
        Runnable r2 = () ->{
            while(true){
            try{
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in)); 
                String send_msg = br1.readLine();
                writer.println(send_msg);
                writer.flush();
            }
            catch(Exception e){
                e.printStackTrace();
            }
         }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        new Client();
    }
} // end of class.

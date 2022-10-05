import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Server{

    ServerSocket serverSocket;
    Socket clientSocket;
    BufferedReader read; //for reading from client.
    PrintWriter write;   //for writing to client.

    //Declaring components
    JFrame frame = new JFrame();
    JLabel heading = new JLabel("Chat Area");
    JTextArea msg_Area = new JTextArea();
    JTextField msg_In = new JTextField();
    Font font = new Font("Lato",Font.PLAIN,15);

    public Server(){
        try{
        serverSocket = new ServerSocket(7777);
        System.out.println("Waiting for connection...");
        clientSocket = serverSocket.accept();
        System.out.println("Connection Established");
        read = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        write = new PrintWriter(clientSocket.getOutputStream());
        GUI();
        events();
        startReading();
        startWriting();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

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
                    write.println(contentSend);
                    write.flush();
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
        frame.setTitle("VChat[Server]");
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
                String msg = read.readLine();
                if(msg.equals("exit"))
                {System.out.println("Client terminated the chat");
                break;}
                //System.out.println("Client: "+msg);
                msg_Area.append("Client: "+msg+"\n");
                }
            catch(Exception e){
                e.printStackTrace();}
            }
            
        };
        new Thread(r1).start();}

    public void startWriting(){
        //Thread -> for sending the data.
        Runnable r2 = () ->{
            System.out.println("Writer started.");
            while(true){
            try{
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in)); 
                String send_msg = br1.readLine();
                write.println(send_msg);
                write.flush();
            }
            catch(Exception e){
                e.printStackTrace();
            }
         }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        new Server();
    }
}

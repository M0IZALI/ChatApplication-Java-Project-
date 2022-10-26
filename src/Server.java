import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Server{
    public String name;
    ServerSocket serverSocket;  
    Socket clientSocket;
    BufferedReader read; //for reading from client.
    PrintWriter write;   //for writing to client.
    ReadThread rt;
    WriteThread wt;

    //Declaring components
    JFrame frame = new JFrame();
    JLabel heading = new JLabel("Chat Area");
    JTextArea msg_Area = new JTextArea();
    JTextField msg_In = new JTextField();
    Font font = new Font("Lato",Font.PLAIN,15);

    public Server(String name){
        this.name = name;
        try{
        serverSocket = new ServerSocket(7777);
        System.out.println("Waiting for connection...");
        new Client(); 
        clientSocket = serverSocket.accept();
        System.out.println("Connection Established");
        read = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        write = new PrintWriter(clientSocket.getOutputStream());
        create_GUI();
        events();
        ReadThread rt = new ReadThread(name, read, frame, msg_In ,msg_Area);
        rt.start();
        WriteThread wt = new WriteThread(read, write);
        wt.start();
        //startReading();
        //startWriting();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

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
    }//end of events.

    public void create_GUI(){
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
    }// end of create_GUI.
}
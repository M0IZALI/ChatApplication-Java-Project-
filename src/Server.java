import java.io.*;
import java.net.*;

public class Server{

    ServerSocket serverSocket;
    Socket clientSocket;
    BufferedReader read; //for reading from client.
    PrintWriter write;   //for writing to client.

    public Server(){
        try{
        System.out.println("Hello");
        serverSocket = new ServerSocket(7777);
        System.out.println("Waiting for connectiong...");
        clientSocket = serverSocket.accept();
        System.out.println("Connection Established");
        read = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        write = new PrintWriter(clientSocket.getOutputStream());
        startReading();
        startWriting();
        }
        catch(Exception e){
            e.printStackTrace();
        }
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
                System.out.println("Client:"+msg);
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
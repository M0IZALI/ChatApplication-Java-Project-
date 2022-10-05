import java.net.*;
import java.io.*;
public class Client {
    
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;

    public Client(){
        try {
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection Established");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        } catch (IOException e){
           e.printStackTrace();
        }
    } // end of constructor.

    public void startReading(){
        //Thread -> for reading the data.
        Runnable r1 = ()->{
            System.out.println("reader started");

            while(true){
                try{
                String msg = reader.readLine();
                if(msg.equals("exit"))
                {System.out.println("Server terminated the chat");
                break;}
                System.out.println("Server:"+msg);
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
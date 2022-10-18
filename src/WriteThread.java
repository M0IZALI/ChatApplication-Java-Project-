import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class WriteThread extends Thread{
   WriteThread(BufferedReader br, PrintWriter writer){
    this.br = br;
    this.writer = writer;
   }
   
    BufferedReader br;
    PrintWriter writer;

    public void run(){
        System.out.println("Writer started");
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
    }
}
import java.io.BufferedReader;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class ReadThread extends Thread{
    ReadThread(String name , BufferedReader reader, JFrame frame , JTextField msg_In , JTextArea msg_Area){
        this.name = name;
        this.reader = reader;
        this.frame = frame;
        this.msg_In = msg_In;
        this.msg_Area = msg_Area;
    }

    String name;
    BufferedReader reader;
    JFrame frame; 
    JTextField msg_In;
    JTextArea msg_Area;

    public void run(){
        System.out.println("Reader started");
        while(true){
            try{
            String msg = reader.readLine();
            if(msg.equals("/exit"))
            {System.out.println(name+" terminated the chat");
            JOptionPane.showMessageDialog(frame, name+" ended the chat");
            msg_In.setEnabled(false);
            break;}
            //System.out.println("Server:"+msg);
            msg_Area.append(name+": "+msg+"\n");
          }
        catch(Exception e){
            e.printStackTrace();}
        }
    }
}

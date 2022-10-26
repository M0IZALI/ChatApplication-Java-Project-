import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login{
    JFrame frame;
    JLabel l1, l2, l3, l4;
    JTextField t1;
    JPasswordField p1;
    JCheckBox showPass;
    JButton login;
    public String name;
   

    public Login(){
        JFrame frame = new JFrame("Vchat");
        frame.setResizable(false);
        frame.setLayout(null);
        //frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.green);
        frame.setSize(600,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        l1 = new JLabel("LOGIN");
        l1.setFont(new Font("Arial Black", Font.BOLD, 75)); 
        l1.setBounds(155,30,300,100);

        l4 = new JLabel();
        l4.setFont(new Font("Serit",Font.ITALIC,20));
        l4.setForeground(Color.red);
        l4.setBounds(150,145,400,20);

        l2 = new JLabel("Username:-");
        l2.setFont(new Font("Serif",Font.PLAIN,20));
        l2.setBounds(100,150,100,100);

        l3 = new JLabel("Password:-");
        l3.setFont(new Font("Serif",Font.PLAIN,20));
        l3.setBounds(100,200,100,100);

        t1 = new JTextField();
        t1.setBounds(210,190,150,20);

        p1 = new JPasswordField();
        p1.setBounds(210,240,150,20);

        showPass = new JCheckBox("show password");
        showPass.setFocusable(false);
        showPass.setBounds(365,237,120,20);
        showPass.setBackground(Color.green);

        login = new JButton("Login");
        login.setFocusable(false);
        login.setBounds(240,300,100,20);

        frame.add(l1);
        frame.add(l4);
        frame.add(l2);
        frame.add(l3);
        frame.add(t1);
        frame.add(p1);
        frame.add(showPass);
        frame.add(login);
      frame.setVisible(true);

      login.addActionListener(new ActionListener(){  //event for login button.

        public void actionPerformed(ActionEvent e){
            Connection con;
            PreparedStatement ps;
            try{
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "knowIchanged");
                ps = con.prepareStatement("select username, pass from userInfo where username=? AND pass=?");
                ps.setString(1, t1.getText());  //precompiled SQL statement
                ps.setString(2, String.valueOf(p1.getPassword()));
                ResultSet result = ps.executeQuery(); 
                if(result.next()){      
                    name = t1.getText();
                    new Server(name);  
                frame.setVisible(false);
                }
                else{
                    l4.setText("Invalid username or password");
                }
            }catch(SQLException e1){
                e1.printStackTrace();
            }   
        }
      });

      showPass.addActionListener(new ActionListener(){  //event for showPass checkbox.
        public void actionPerformed(ActionEvent e){
            if(showPass.isSelected()) //echo char, you can set a character that would appear whenever a user will type the pass.
            p1.setEchoChar((char)0);
            else
            p1.setEchoChar('*');
        }    
     });
     
    }// end of constructor
}// end of class Login

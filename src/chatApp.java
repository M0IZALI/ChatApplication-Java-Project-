import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class chatApp extends JFrame{
	public chatApp() {
		ImageIcon ic=new ImageIcon("image.png");
		JPanel jp=new JPanel();
		jp.setLayout(new BorderLayout());
		JLabel lb=new JLabel(ic);
		lb.setIcon(ic);
		jp.add(lb, BorderLayout.CENTER);
		
		JProgressBar pb=new JProgressBar();
		pb.setStringPainted(true);
		pb.setMaximum(0);
		pb.setMaximum(100);
		jp.add(pb, BorderLayout.SOUTH);
		
		add(jp);
		
		setTitle("ChatApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setContentPane(jp);
	    setLocation(200,300);
	    setVisible(true);
	    
		setSize(400,300);
		
		for(int i=0; i<=100; i++){
			pb.setValue(i);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();}
			if (i == 100){
				setVisible(false);
				new Client();
			}
		}//end of for loop
	}// end of constructor.
	public static void main(String args[]) {
		new chatApp();
	}
}// end of class.

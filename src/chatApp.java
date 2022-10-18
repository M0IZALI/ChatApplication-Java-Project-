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
		pb.setMinimum(0);
		pb.setMaximum(100);
		jp.add(pb, BorderLayout.SOUTH);
		
		add(jp);

		setSize(1280,720);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	    
		for(int i=0; i<=100; i++){
			try{
				Thread.sleep(20 );
			} catch(InterruptedException e){
				e.printStackTrace();
			}
			pb.setValue(i);
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

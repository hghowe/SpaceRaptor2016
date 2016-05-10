import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class ClientFrame extends JFrame {

	private GamePanel myPanel;
	
	public ClientFrame() 
	{
		super("StarRaptor");
		setSize(800,800);
		setBackground(Color.BLACK);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(1,1));
		myPanel = new GamePanel();
		getContentPane().add(myPanel);
		addKeyListener(myPanel);
		setVisible(true);
	}


}

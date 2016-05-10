import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener{

	private Map<Integer,Transmittable> objectsOnScreen;
	private boolean leftPressed, rightPressed, thrustPressed, firePressed;
	private StarRaptorClient theClient;
	
	public GamePanel() {
		super();
		objectsOnScreen = new HashMap<Integer,Transmittable>();
		leftPressed = false;
		rightPressed = false;
		thrustPressed = false;
		firePressed = false;
		theClient = new StarRaptorClient(objectsOnScreen, requestInitials(), this);
		setBackground(Color.BLACK);
	}

	/**
	 * draws the screen. This will automatically be called shortly after repaint(), on a regular cycle.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (Integer key:objectsOnScreen.keySet())
		{
			if (objectsOnScreen.containsKey(key))
				((Drawable)(objectsOnScreen.get(key))).drawSelf(g);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// this is required to have this method because we are implementing KeyListener.
		//  (but we don't have to do anything with it....
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_LEFT)
			leftPressed = true;
		else if (e.getKeyCode()== KeyEvent.VK_RIGHT)
			rightPressed = true;
		else if (e.getKeyCode()== KeyEvent.VK_UP)
			thrustPressed = true;
		else if (e.getKeyCode()== KeyEvent.VK_SPACE)
			firePressed = true;
		else
			return;
		theClient.sendKeyBoardStateString(leftPressed, rightPressed, thrustPressed, firePressed);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_LEFT)
			leftPressed = false;
		else if (e.getKeyCode()== KeyEvent.VK_RIGHT)
			rightPressed = false;
		else if (e.getKeyCode()== KeyEvent.VK_UP)
			thrustPressed = false;
		else if (e.getKeyCode()== KeyEvent.VK_SPACE)
			firePressed = false;
		else
			return;
		theClient.sendKeyBoardStateString(leftPressed, rightPressed, thrustPressed, firePressed);
	}
	
	/**
	 * asks the user for his/her name. 
	 * @return a non-empty String.
	 */
	public String requestInitials()
	{
		String initials;
		do
		{
		   initials = JOptionPane.showInputDialog("Please enter your initials.");
		} while (initials == null || 
				initials.equals("") ||
				initials.indexOf(Constants.MJR_DIVIDER)>-1 ||
				initials.indexOf(Constants.MNR_DIVIDER)>-1);
		return initials.substring(0,Math.min(3, initials.length())).toUpperCase();
	}
}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener{

	private Map<Integer,Transmittable> objectsOnScreen;
	private boolean leftPressed, rightPressed, thrustPressed, firePressed;
	private StarRaptorClient theClient;
	private ArrayList<String> objectsToUpdate;
	private ArrayList<Transmittable> objectsToAdd;
	private ArrayList<Integer> objectsToRemove;
	
	public GamePanel() {
		super();
		objectsOnScreen = new HashMap<Integer,Transmittable>();
		leftPressed = false;
		rightPressed = false;
		thrustPressed = false;
		firePressed = false;
		theClient = new StarRaptorClient(requestInitials(), this);
		setBackground(Color.BLACK);
		objectsToUpdate = new ArrayList<String>();
		objectsToAdd = new ArrayList<Transmittable>();
		objectsToRemove = new ArrayList<Integer>();
	}

	public void addObject(Transmittable obj)
	{
		objectsToAdd.add(obj);
	}
	
	public void updateObject(String s)
	{
		objectsToUpdate.add(s);
	}
	
	public void removeObject(int i)
	{
		objectsToRemove.add(i);
	}
	/**
	 * takes the changes queued up in objectsToAdd/Update/Remove and makes these changes
	 * to objectsOnScreen.
	 */
	public void updateObjectsOnScreen()
	{
		while (!objectsToAdd.isEmpty())
		{
			Transmittable obj = objectsToAdd.remove(0);
			int id = Integer.parseInt(obj.longDescription().split(Constants.MNR_DIVIDER)[0]);
			objectsOnScreen.put(id, obj);
		}
		while (!objectsToUpdate.isEmpty())
		{
			String shortDesc = objectsToUpdate.remove(0);
			int id = Integer.parseInt(shortDesc.split(Constants.MNR_DIVIDER)[0]);
			objectsOnScreen.get(id).updateFromDescription(shortDesc);
		}
		while (!objectsToRemove.isEmpty())
		{
			objectsOnScreen.remove(objectsToRemove.remove(0));
		}
	}
	
	/**
	 * draws the screen. This will automatically be called shortly after repaint(), on a regular cycle.
	 * first updates the contents of objectsOnScreen.
	 */
	public void paintComponent(Graphics g)
	{
		updateObjectsOnScreen();
		super.paintComponent(g);
		for (Integer key:objectsOnScreen.keySet())
		{
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

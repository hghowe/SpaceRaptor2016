import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener{

	private Map<Integer,Transmittable> objectsOnScreen;
	private boolean leftPressed, rightPressed, thrustPressed, firePressed;
	
	
	public GamePanel() {
		super();
		objectsOnScreen = new HashMap<Integer,Transmittable>();
		leftPressed = false;
		rightPressed = false;
		thrustPressed = false;
		firePressed = false;
		ClientRaptor testRaptor = new ClientRaptor();
		testRaptor.buildFromDescription("0\tHGH\t400\t400\t0.5235");
		objectsOnScreen.put(testRaptor.getId(),testRaptor);
		ClientRaptor testRaptor2 = new ClientRaptor();
		testRaptor2.buildFromDescription("1\tHGH\t400\t450\t-1.047");
		testRaptor2.setPlayer(true);
		objectsOnScreen.put(testRaptor2.getId(),testRaptor2);
	}

	public void paintComponent(Graphics g)
	{
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
		if (e.getKeyCode()== KeyEvent.VK_RIGHT)
			rightPressed = true;
		if (e.getKeyCode()== KeyEvent.VK_UP)
			thrustPressed = true;
		if (e.getKeyCode()== KeyEvent.VK_SPACE)
			firePressed = true;
		System.out.println(""+leftPressed+rightPressed+thrustPressed+firePressed);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_LEFT)
			leftPressed = false;
		if (e.getKeyCode()== KeyEvent.VK_RIGHT)
			rightPressed = false;
		if (e.getKeyCode()== KeyEvent.VK_UP)
			thrustPressed = false;
		if (e.getKeyCode()== KeyEvent.VK_SPACE)
			firePressed = false;
	}
	
}

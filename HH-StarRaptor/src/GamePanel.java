import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class GamePanel extends JPanel{

	private Map<Integer,Transmittable> objectsOnScreen;
	
	public GamePanel() {
		super();
		objectsOnScreen = new HashMap<Integer,Transmittable>();
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
	
}

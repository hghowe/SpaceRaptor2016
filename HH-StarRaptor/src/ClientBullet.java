import java.awt.Color;
import java.awt.Graphics;

public class ClientBullet extends AbstractBullet implements Drawable {

	public ClientBullet() {
		super();
	}
	
	public void drawSelf(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.drawOval((int)getxPos()-1, (int)getyPos()-1, 2, 2);
	}

}

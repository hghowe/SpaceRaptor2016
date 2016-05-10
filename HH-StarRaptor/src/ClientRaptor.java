import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class ClientRaptor extends AbstractRaptor implements Drawable{

	private boolean isPlayer;
	
	public ClientRaptor() {
		super();
		isPlayer = false;
	}

	/**
	 * @return the isPlayer
	 */
	public boolean isPlayer() {
		return isPlayer;
	}

	/**
	 * @param isPlayer the isPlayer to set
	 */
	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public void drawSelf(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		if (this.isPlayer)
			g2d.setColor(new Color(255,255,255));
		else
			g2d.setColor(new Color(255,0,0));
		AffineTransform AF = g2d.getTransform();
			g2d.translate(getxPos(), getyPos());
			g2d.rotate(getAngle());
			g2d.drawLine(4, 0, -3, -2);
			g2d.drawLine(-3, -2, -1, 0);
			g2d.drawLine(-1, 0, -3, 2);
			g2d.drawLine(-2, 2, 4, 0);
		g2d.setTransform(AF);
	}
	
	

}

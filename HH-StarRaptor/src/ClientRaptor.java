import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Date;

public class ClientRaptor extends AbstractRaptor implements Drawable{

	private boolean isPlayer;
	private Font initialFont;
	private Date lastTime;
	private double deltaT;
	private double vx,vy;
	
	
	public ClientRaptor() {
		super();
		isPlayer = false;
		initialFont = new Font("Arial",Font.PLAIN,8);
		lastTime = new Date();
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
		g2d.setFont(initialFont);
		
		AffineTransform AF = g2d.getTransform();
			g2d.translate(getxPos(), getyPos());
			int stringWidth = g2d.getFontMetrics().stringWidth(getInitials());
			g2d.drawString(getInitials(), -stringWidth/2, -10);
			g2d.rotate(getAngle());
			g2d.drawLine(4, 0, -3, -2);
			g2d.drawLine(-3, -2, -1, 0);
			g2d.drawLine(-1, 0, -3, 2);
			g2d.drawLine(-2, 2, 4, 0);
		g2d.setTransform(AF);
		
	}
		
	public void setxPos(double xPos) 
	{
		Date now = new Date();
		deltaT = (now.getTime() - lastTime.getTime())/1000.0
				
				.;
		vx = (super.getxPos() - xPos)/deltaT;
		super.setxPos(xPos);
	}
	
	public void setyPos(double yPos) 
	{
		vy = (super.getyPos() - yPos)/deltaT;
		super.setyPos(yPos);
		deltaT = 0;
		lastTime = new Date();
	}
	
	
	
	

}

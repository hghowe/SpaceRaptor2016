import java.awt.Graphics;

public class ClientRaptor extends AbstractRaptor{

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
		
		
	}
	
	

}

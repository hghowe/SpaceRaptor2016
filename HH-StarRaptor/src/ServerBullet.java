
public class ServerBullet extends AbstractBullet implements Steppable{

	private double lifetimeRemaining;
	private double vx, vy;
	
	public ServerBullet() {
		super();
		lifetimeRemaining = Constants.BULLET_LIFETIME;
		vx = 0;
		vy = 0;
	}
	
	public void step(double deltaT)
	{
		System.out.println("Bullet stepping.");
		lifetimeRemaining -= deltaT;
		setxPos(getxPos() + vx*deltaT);
		setyPos(getyPos() + vy*deltaT);
		
		if (getxPos() < 0)
			setxPos(getxPos()+Constants.SCREEN_WIDTH);
		if (getxPos() > Constants.SCREEN_WIDTH)
			setxPos(getxPos()-Constants.SCREEN_WIDTH);
		if (getyPos() < 0)
			setyPos(getyPos()+Constants.SCREEN_HEIGHT);
		if (getyPos() > Constants.SCREEN_HEIGHT)
			setyPos(getyPos()-Constants.SCREEN_WIDTH);
	}
	
	public boolean isAlive() {return lifetimeRemaining > 0;}

	/**
	 * @return the vx
	 */
	public double getVx() {
		return vx;
	}

	/**
	 * @param vx the vx to set
	 */
	public void setVx(double vx) {
		this.vx = vx;
	}

	/**
	 * @return the vy
	 */
	public double getVy() {
		return vy;
	}

	/**
	 * @param vy the vy to set
	 */
	public void setVy(double vy) {
		this.vy = vy;
	}
	
	
	

}

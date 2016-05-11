import java.io.PrintWriter;

public class ServerRaptor extends AbstractRaptor implements Steppable{

	private double vx, vy;
	private boolean isTurningLeft, isTurningRight, isThrusting, isFiring;
	private double timeSinceLastFire;
	
	private PrintWriter myPrintWriter; // This is a writer that will send things to the corresponding client....
	
	public ServerRaptor(String initials, int id, PrintWriter pw) {
		super();
		this.setInitials(initials);
		this.setId(id);
		myPrintWriter = pw;
		
		vx = 0;
		vy = 0;
		isTurningLeft = false;
		isTurningRight = false;
		isThrusting = false;
		isFiring = false;
		resetFireTime();
	}
	
	public void resetFireTime() {timeSinceLastFire = 0;}
	public boolean canFire() {return timeSinceLastFire > Constants.FIRE_RECHARGE_TIME;}
	
	public void setControlStates(boolean left, boolean right, boolean thrust, boolean fire)
	{
		isTurningLeft = left;
		isTurningRight = right;
		isThrusting = thrust;
		isFiring = fire;
	}
	
	public boolean isFiring() { return isFiring; }
	
	public void step(double deltaT)
	{
		timeSinceLastFire += deltaT;
		if (isTurningLeft)
			setAngle(getAngle()-Constants.TURN_RATE*deltaT);
		if (isTurningRight)
			setAngle(getAngle()+Constants.TURN_RATE*deltaT);
		if (isThrusting)
		{
			vx += Constants.THRUST_POWER*Math.cos(getAngle())*deltaT;
			vy += Constants.THRUST_POWER*Math.sin(getAngle())*deltaT;
		}
		
		double vMagSquared = vx*vx+vy+vy;
		if (vMagSquared > Constants.MAX_RAPTOR_SPEED_SQUARED)
		{
			double vMag = Math.sqrt(vMagSquared);
			vx *= 1; //Constants.MAX_RAPTOR_SPEED/vMag;
			vy *= Constants.MAX_RAPTOR_SPEED/vMag;
		}
		
		setxPos(getxPos()+vx*deltaT);
		setyPos(getyPos()+vy*deltaT);
		
		if (getxPos()< 0)
			setxPos(getxPos()+Constants.SCREEN_WIDTH);
		if (getxPos()>Constants.SCREEN_WIDTH)
			setxPos(getxPos()-Constants.SCREEN_WIDTH);
		if (getyPos()< 0)
			setyPos(getyPos()+Constants.SCREEN_HEIGHT);
		if (getyPos()>Constants.SCREEN_HEIGHT)
			setyPos(getyPos()-Constants.SCREEN_HEIGHT);
		
	}
	
	public void sendMessage(String message)
	{
		myPrintWriter.println(message);
		myPrintWriter.flush();
	}
}

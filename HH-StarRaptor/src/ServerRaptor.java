import java.io.PrintWriter;

public class ServerRaptor extends AbstractRaptor implements Steppable{

	private double vx, vy;
	private boolean isTurningLeft, isTurningRight, isThrusting, isFiring;
	
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
	}
	
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
		if (isTurningLeft)
			setAngle(getAngle()-Constants.TURN_RATE*deltaT);
		if (isTurningRight)
			setAngle(getAngle()+Constants.TURN_RATE*deltaT);
		if (isThrusting)
		{
			vx += Constants.THRUST_POWER*Math.cos(getAngle())*deltaT;
			vy += Constants.THRUST_POWER*Math.sin(getAngle())*deltaT;
		}
		
		setxPos(getxPos()+vx*deltaT);
		setyPos(getyPos()+vy*deltaT);
	}
	
	public void sendMessage(String message)
	{
		myPrintWriter.println(message);
		myPrintWriter.flush();
	}
}

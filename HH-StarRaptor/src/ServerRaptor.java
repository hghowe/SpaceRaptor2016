
public class ServerRaptor extends AbstractRaptor {

	private double vx, vy;
	private boolean isTurningLeft, isTurningRight, isThrusting;
	
	
	public ServerRaptor() {
		super();
		vx = 0;
		vy = 0;
		isTurningLeft = false;
		isTurningRight = false;
		isThrusting = false;
	}
	
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
}


public class Particle {

	
	private int x,
				y,
				v,
				a,
				life;
	
	public Particle(int xLoc, int yLoc, int vel, int angle, int tickLife) 
	{
		x = xLoc;
		y = yLoc;
		v = vel;
		a = angle;
		life = tickLife;
	}
	
	public void step(int ticks)
	{
		
	}
}
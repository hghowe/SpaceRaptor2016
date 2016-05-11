import java.util.ArrayList;

public class ParticleFountain 
{
	
	private ArrayList<int[]> children;
	private int particleMeanLife;
	private int particleLifeDeviance;
	private int xLoc,yLoc; //clientRaptors know where they are, but do not know their velocity 
	private int particleMeanVelocity;
	private int particleMeanAngle;
	
	public ParticleFountain(int particleMeanTicks, int lifeDeviance) 
	{
		particleMeanLife = particleMeanTicks;
		particleLifeDeviance = lifeDeviance;
	}

}

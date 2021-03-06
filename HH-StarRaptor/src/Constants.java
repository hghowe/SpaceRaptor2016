
public interface Constants {
	public final String MJR_DIVIDER = "<>";
	public final String MNR_DIVIDER = "\t";
	
	public final int PREFIX_NEW_OBJECT = 0;
	public final int PREFIX_UPDATE_OBJECT = 1;
	public final int PREFIX_REMOVE_OBJECT = 2;
	
	public final int TYPE_RAPTOR = 0;
	public final int TYPE_BULLET = 1;
	
	public final String IP_ADDRESS = "172.16.220.122";
	
	public final double TURN_RATE = 3.14159;
	public final double THRUST_POWER = 100;
	
	public final int SCREEN_WIDTH = 800;
	public final int SCREEN_HEIGHT = 800;
	
	public final int MAX_RAPTOR_SPEED = 200;
	public final int MAX_RAPTOR_SPEED_SQUARED = 40000;
	
	public final double BULLET_LIFETIME = 1.75;
	public final double FIRE_RECHARGE_TIME = 0.5;
	public final double BULLET_SPEED = 100; // the relative speed of the bullet, compared to the raptor.
	public final double BULLET_LAUNCH_OFFSET = 10; // the distance in front of the raptor where the bullet appears
	
	public final int RAPTOR_BULLET_COLLISION_DISTANCE_SQUARED = 25;
	public final double HEALTH_LOSS_PER_HIT = 0.0667;
}

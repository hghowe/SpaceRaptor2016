
public class AbstractRaptor implements Transmittable {

	private int id;
	private String initials;
	private String color;
	private double xPos, yPos, angle;
	
	public AbstractRaptor() {
		id = -1;
		initials = "AAA";
		color = "RED";
		xPos = 0;
		yPos = 0;
		angle = 0;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * @param initials the initials to set
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}
	
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the xPos
	 */
	public double getxPos() {
		return xPos;
	}

	/**
	 * @param xPos the xPos to set
	 */
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	/**
	 * @return the yPos
	 */
	public double getyPos() {
		return yPos;
	}

	/**
	 * @param yPos the yPos to set
	 */
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 * postcondition: angle is in range 0 ≤ angle < 2π
	 */
	public void setAngle(double angle) {
		this.angle = angle;
		while (this.angle<0)
			this.angle += 2*Math.PI;
		while (this.angle>=2*Math.PI)
			this.angle -= 2*Math.PI;
	}

	/** (fulfilling Transmittable)
	 * create a string describing just the information needed to <i>update</i> this object
	 * @return description
	 */
	public String shortDescription()
	{
		return  id+Constants.MNR_DIVIDER+
				xPos+Constants.MNR_DIVIDER+
				yPos+Constants.MNR_DIVIDER+
				angle;
	}
	
	/** (fulfilling Transmittable)
	 * create a string describing the information needed to <i>create</i> this object
	 * @return description
	 */
	public String longDescription()
	{
		return  id+Constants.MNR_DIVIDER+
				initials+Constants.MNR_DIVIDER+
				xPos+Constants.MNR_DIVIDER+
				yPos+Constants.MNR_DIVIDER+
				angle+Constants.MNR_DIVIDER+
				color;
	}
	
	/** (fulfilling Transmittable)
	 * set the fields for this class, based on a description string of the form 
	 * made by longDescription().
	 * @param longDesc
	 */
	public void buildFromDescription(String longDesc)
	{
		String [] parts = longDesc.split(Constants.MNR_DIVIDER);
		setId(Integer.parseInt(parts[0]));
		setInitials(parts[1]);
		setxPos(Double.parseDouble(parts[2]));
		setyPos(Double.parseDouble(parts[3]));
		setAngle(Double.parseDouble(parts[4]));
		setColor(parts[5]);
	}
	
	/** (fulfilling Transmittable)
	 * update the fields for this class, based on a description string of the form 
	 * made by shortDescription().
	 * @param shortDesc
	 */
	public void updateFromDescription(String shortDesc)
	{
		String [] parts = shortDesc.split(Constants.MNR_DIVIDER);
		setId(Integer.parseInt(parts[0]));
		setxPos(Double.parseDouble(parts[1]));
		setyPos(Double.parseDouble(parts[2]));
		setAngle(Double.parseDouble(parts[3]));
	}
	
	
}

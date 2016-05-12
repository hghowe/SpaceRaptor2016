
public abstract class AbstractBullet implements Transmittable {

	private int id;
	private double xPos, yPos;
	
	
	public AbstractBullet() {
		id = -1;
		xPos = 0;
		yPos = 0;
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
	 * create a string describing just the information needed to <i>update</i> this object
	 * @return description
	 */
	public String shortDescription()
	{ return id+Constants.MNR_DIVIDER+xPos+Constants.MNR_DIVIDER+yPos; }
	
	/**
	 * create a string describing the information needed to <i>create</i> this object
	 * @return description
	 */
	public String longDescription()
	{
		return id+Constants.MNR_DIVIDER+xPos+Constants.MNR_DIVIDER+yPos;
	}
	
	/**
	 * set the fields for this class, based on a description string of the form 
	 * made by longDescription().
	 * @param longDesc
	 */
	public void buildFromDescription(String longDesc)
	{
		String[] parts = longDesc.split(Constants.MNR_DIVIDER);
		id = Integer.parseInt(parts[0]);
		xPos = Double.parseDouble(parts[1]);
		yPos = Double.parseDouble(parts[2]);
	}
	
	/**
	 * update the fields for this class, based on a description string of the form 
	 * made by shortDescription().
	 * @param shortDesc
	 */
	public void updateFromDescription(String shortDesc)
	{
		String[] parts = shortDesc.split(Constants.MNR_DIVIDER);
		xPos = Double.parseDouble(parts[1]);
		yPos = Double.parseDouble(parts[2]);
	}
	
	
}

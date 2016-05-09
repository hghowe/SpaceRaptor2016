
public class AbstractRaptor {

	private double xPos, yPos, angle;
	
	public AbstractRaptor() {
		xPos = 0;
		yPos = 0;
		angle = 0;
	}

	public String setFromString(String s)
	{
		String[] parts = s.split("\t");
		xPos = Double.parseDouble(parts[0]);
		yPos = Double.parseDouble(parts[1]);
		angle = Double.parseDouble(parts[2]);
		String remains = "";
		for (int i = 3; i<parts.length; i++)
		{
			remains+= parts[i];
			if (i<parts.length-1)
				remains+="\t";
		}
		return remains;
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
			this.angle+=2*Math.PI;
		while (this.angle>=2*Math.PI)
			this.angle-=2*Math.PI;
	}

	public String toString()
	{
		return xPos+"\t"+yPos+"\t"+angle;
	}
	
}
